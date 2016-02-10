package com.im.test
import spock.lang.Specification
import spock.lang.Unroll

class UserSpec extends Specification {

    def "test Get Full Name"(fname, lname, fullname) {
        given:
        User uname = new User(firstName: fname, lastName: lname);

        expect:
        uname.getFullName() == fullname
        where:
        fname  | lname    | fullname
        null   | null     | null
        "amit" | null     | null
        null   | "raturi" | null
        "amit" | "raturi" | "amit raturi"


    }

    def "display Name with gender"(String givenGender, result) {
        given:
        User ugend = new User(firstName: "Abs", lastName: "lname", gender: givenGender);
        expect:
        if (result == null) {
            ugend.displayName() == result
        } else {
            ugend.displayName() == result + "Abs" + " " + "lname"
        }

        where:
        givenGender | result
        "Male"      | "Mr"
        "Female"    | "Ms"
        "yahoo"     | null
        "yahoo"     | null
        null        | null
        null        | null


    }

    def "Validating Password"(pwd, result) {
        given:
        User pass = new User();
        expect:
        pass.isValidPassword(pwd) == result
        where:
        pwd        | result
        "abc"      | false
        "12345678" | true

    }

    @Unroll
    def "Reset Password And SendEmail"() {

        given:
        User user = new User()
        and:
        def mockedEmailService = Mock(EmailService)
        user.emailService = mockedEmailService
        and:
        1 * mockedEmailService.sendCancellationEmail(_ as User, _ as String)
        expect:

        user.resetPasswordAndSendEmail()


    }

    def "encyrypt Password"() {
        given:
        User user = new User()
        and:

        user.passwordEncrypterService = Mock(PasswordEncrypterService)
        Mock(PasswordEncrypterService).encrypt(String) >> { "pass" }
        expect:
        user.encyryptPassword("pass") == null
    }

    def "Income Group"(BigDecimal income, String result) {
        given:
        User user = new User(incomePerMonth: income)
        expect:
        user.getIncomeGroup() == result
        where:
        income | result
        2000   | "MiddleClass"
        7000   | "Higher MiddleClass"
        12000  | "Higher MiddleClass"
    }

    def "Purchase"() {
        given:
        User user = new User();
        and:
        Product p = new Product();
        when:
        user.purchase(p)
        then:
        user.purchasedProducts.size >= 1
    }

    def "cancel Purchase"() {
        setup:
        User user = new User();
        and:
        Product p = new Product();
        and:
        user.purchasedProducts.add(p)
        when:
        user.purchasedProducts.remove(p)
        then:
        user.purchasedProducts.empty

    }

}
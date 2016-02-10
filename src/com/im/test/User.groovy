package com.im.test


class User {
    PasswordEncrypterService passwordEncrypterService = new PasswordEncrypterService()
    EmailService emailService = new EmailService()

    String username
    String password
    String firstName
    String lastName
    String gender = "Male"
    BigDecimal balance
    List<Product> purchasedProducts = []


    Boolean isPrivellegedCustomer
    BigDecimal incomePerMonth

    String getFullName() {
        if(((firstName=="")||(lastName==""))||((firstName==null)||(lastName==null)))
        {
            return null
        }
        else {
            return firstName + " " + lastName
        }
    }

    String displayName() {
        String prefix
        if(gender == "Male")
        {
            return 'Mr'+ fullName

        }
        else if(gender == "Female")
        {
            return 'Ms'+ fullName
        }
        else
        {
            return null
        }

    }

    Boolean isValidPassword(String pwd) {
        Boolean isValid = false
        if (pwd && pwd.length() > 7) {
            isValid = true
        }
        return isValid
    }

    void resetPasswordAndSendEmail() {
        String newPassword = "dummy"
        this.password = encyryptPassword(newPassword)
        emailService.sendCancellationEmail(this, newPassword)

    }

    String encyryptPassword(String pwd) {
        String encryptedPassword
        if (this.isValidPassword(pwd)) {
            encryptedPassword = passwordEncrypterService.encrypt(pwd)
        }
        println encryptedPassword
        return encryptedPassword
    }


    String getIncomeGroup() {
        String group
        if (this.incomePerMonth <= 5000) {
            group = "MiddleClass"
        } else if (incomePerMonth > 5000 && incomePerMonth <= 10000) {
            group = "Higher MiddleClass"
        } else if (incomePerMonth > 10000) {
            group = "Higher MiddleClass"
        }
        return group
    }


    void purchase(Product p) {
        if (p) {
            purchasedProducts.add(p)
        }
    }

    void cancelPurchase(Product p) {
        if (p) {
            purchasedProducts.remove(p)
        }
    }



}

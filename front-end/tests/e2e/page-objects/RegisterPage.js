module.exports = {
  url: function () {
    return this.api.launchUrl + 'register'
  },
  elements: {
    app: {
      selector: '#app'
    },
    logoImage: {
      selector: 'img.logo'
    },
    usernameInput: {
      selector: '#username'
    },
    emailAddressInput: {
      selector: '#emailAddress'
    },
    firstNameInput: {
      selector: '#firstName'
    },
    lastNameInput: {
      selector: '#lastName'
    },
    passwordInput: {
      selector: '#password'
    },
    submitButton: {
      selector: 'button[type=submit]'
    },
    formError: {
      selector: '.failed'
    }
  },
  commands: [{
    register: function (username, emailAddress, firstName, lastName, password) {
      this
        .setValue('@usernameInput', username)
        .setValue('@emailAddressInput', emailAddress)
        .setValue('@firstNameInput', firstName)
        .setValue('@lastNameInput', lastName)
        .setValue('@passwordInput', password)
        .click('@submitButton')
    }
  }]
}

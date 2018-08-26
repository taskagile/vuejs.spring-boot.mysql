module.exports = {
  url: function () {
    return this.api.launchUrl + 'login'
  },
  elements: {
    app: '#app',
    logoImage: 'img.logo',
    usernameInput: '#username',
    passwordInput: '#password',
    submitButton: 'button[type=submit]',
    formError: '.failed'
  },
  commands: [{
    login: function (username, password) {
      this
        .setValue('@usernameInput', username)
        .setValue('@passwordInput', password)
        .click('@submitButton')
    }
  }]
}

const data = require('../data/user')

module.exports = {
  'login page renders elements': function (browser) {
    const loginPage = browser.page.LoginPage()

    loginPage
      .navigate()
      .waitForElementVisible('@app', 30000)
      .assert.visible('@usernameInput')
      .assert.visible('@passwordInput')
      .assert.visible('@submitButton')
      .assert.hidden('@formError')

    browser.end()
  },
  'login with invalid credentials': function (browser) {
    const loginPage = browser.page.LoginPage()
    loginPage
      .navigate()
      .login('not-exist', 'incorrect')

    browser.pause(2000)

    loginPage
      .assert.visible('@formError')
      .assert.containsText('@formError', 'Invalid credentials')

    browser
      .assert.urlEquals(browser.launchUrl + 'login')
      .end()
  },
  'login with username': function (browser) {
    const loginPage = browser.page.LoginPage()
    const homePage = browser.page.HomePage()
    loginPage
      .navigate()
      .login(data.username, data.password)

    browser.pause(2000)

    homePage
      .navigate()
      .assert.visible('@logoImage')

    browser.end()
  },
  'login with email address': function (browser) {
    const loginPage = browser.page.LoginPage()
    const homePage = browser.page.HomePage()
    loginPage
      .navigate()
      .login(data.emailAddress, data.password)

    browser.pause(2000)

    homePage
      .navigate()
      .assert.visible('@logoImage')

    browser.end()
  }
}

module.exports = {
  'login test': function (browser) {
    browser
      .url(process.env.VUE_DEV_SERVER_URL + 'login')
      .waitForElementVisible('#app', 5000)
      .assert.attributeContains('.logo', 'src', '/static/images/logo.png')
      .end()
  }
}

module.exports = {
  test_settings: {
    default: {
      launch_url: (process.env.LAUNCH_URL || process.env.VUE_DEV_SERVER_URL)
    },
    chrome: {
      desiredCapabilities: {
        browserName: "chrome",
        chromeOptions: {
          args:  ["headless", "no-sandbox", "disable-gpu"]
        }
      }
    }
  },
  page_objects_path: 'tests/e2e/page-objects',
}

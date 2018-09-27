module.exports = {
  devServer: {
    port: 3000,
    proxy: {
      '/api/*': {
        target: 'http://localhost:8080'
      },
      '/rt/*': {
        target: 'http://localhost:8080'
      },
      '/local-file/*': {
        target: 'http://localhost:8080'
      }
    }
  },
  configureWebpack: {
    entry: {
      app: './src/main.js',
      style: [
        'bootstrap/dist/css/bootstrap.min.css',
        'blueimp-file-upload/css/jquery.fileupload.css',
        'noty/lib/noty.css',
        'noty/lib/themes/relax.css'
      ]
    }
  }
}

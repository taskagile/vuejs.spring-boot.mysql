import axios from 'axios'

export default {
  /**
   * Authenticate a login request
   * @param {Object} detail login detail
   */
  authenticate (detail) {
    return new Promise((resolve, reject) => {
      axios.post('/authentications', detail).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(error)
      })
    })
  }
}

import axios from 'axios'

export default {
  /**
   * Register a new user
   * @param {Object} detail registration detail
   */
  register (detail) {
    return new Promise((resolve, reject) => {
      axios.post('/registrations', detail).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(error)
      })
    })
  }
}

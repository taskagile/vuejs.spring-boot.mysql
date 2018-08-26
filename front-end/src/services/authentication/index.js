import axios from 'axios'
import errorParser from '@/utils/error-parser'

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
        reject(errorParser.parse(error))
      })
    })
  }
}

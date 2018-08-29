import axios from 'axios'
import errorParser from '@/utils/error-parser'

export default {
  /**
   * Fetch current user's name, boards, and teams
   */
  getMyData () {
    return new Promise((resolve, reject) => {
      axios.get('/me').then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }
}

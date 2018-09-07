import axios from 'axios'
import errorParser from '@/utils/error-parser'
import eventBus from '@/event-bus'

export default {
  /**
   * Fetch current user's name, boards, and teams
   */
  getMyData () {
    return new Promise((resolve, reject) => {
      axios.get('/me').then(({data}) => {
        resolve(data)
        eventBus.$emit('myDataFetched', data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  },
  signOut () {
    return new Promise((resolve, reject) => {
      axios.post('/me/logout').then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }
}

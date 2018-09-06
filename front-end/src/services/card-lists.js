import axios from 'axios'
import errorParser from '@/utils/error-parser'

export default {
  /**
   * Add a new card list
   * @param {*} detail the card list detail
   */
  add (detail) {
    return new Promise((resolve, reject) => {
      axios.post('/card-lists', detail).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  },
  changePositions (positionChanges) {
    return new Promise((resolve, reject) => {
      axios.post('/card-lists/positions', positionChanges).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }
}

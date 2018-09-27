import axios from 'axios'
import errorParser from '@/utils/error-parser'

export default {
  /**
   * Add a new card
   * @param {*} detail the card detail
   */
  add (detail) {
    return new Promise((resolve, reject) => {
      axios.post('/cards', detail).then(({data}) => {
        resolve(data)
      }).catch(error => {
        reject(errorParser.parse(error))
      })
    })
  },
  changePositions (positionChanges) {
    return new Promise((resolve, reject) => {
      axios.post('/cards/positions', positionChanges).then(({data}) => {
        resolve(data)
      }).catch(error => {
        reject(errorParser.parse(error))
      })
    })
  },
  getCard (cardId) {
    return new Promise((resolve, reject) => {
      axios.get('/cards/' + cardId).then(({data}) => {
        resolve(data)
      }).catch(error => {
        reject(errorParser.parse(error))
      })
    })
  },
  changeCardTitle (cardId, title) {
    return new Promise((resolve, reject) => {
      axios.put('/cards/' + cardId + '/title', {title}).then(({data}) => {
        resolve(data)
      }).catch(error => {
        reject(errorParser.parse(error))
      })
    })
  },
  changeCardDescription (cardId, description) {
    return new Promise((resolve, reject) => {
      axios.put('/cards/' + cardId + '/description', {description}).then(({data}) => {
        resolve(data)
      }).catch(error => {
        reject(errorParser.parse(error))
      })
    })
  },
  addCardComment (cardId, comment) {
    return new Promise((resolve, reject) => {
      axios.post('/cards/' + cardId + '/comments', {comment}).then(({data}) => {
        resolve(data)
      }).catch(error => {
        reject(errorParser.parse(error))
      })
    })
  },
  getCardActivities (cardId) {
    return new Promise((resolve, reject) => {
      axios.get('/cards/' + cardId + '/activities').then(({data}) => {
        resolve(data)
      }).catch(error => {
        reject(errorParser.parse(error))
      })
    })
  },
  getCardAttachments (cardId) {
    return new Promise((resolve, reject) => {
      axios.get('/cards/' + cardId + '/attachments').then(({data}) => {
        resolve(data)
      }).catch(error => {
        reject(errorParser.parse(error))
      })
    })
  }
}

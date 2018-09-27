import Noty from 'noty'

const showError = function (errorMessage) {
  new Noty({
    type: 'error',
    theme: 'relax',
    closeWith: ['click', 'button'],
    text: errorMessage
  }).show()
}

const closeAll = function () {
  Noty.closeAll()
}

export default {
  error: showError,
  closeAll: closeAll
}

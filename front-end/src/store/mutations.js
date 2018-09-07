export default {
  updateMyData (state, data) {
    state.user.name = data.user.name
    state.user.authenticated = true
    state.teams = data.teams
    state.boards = data.boards
  },
  logout (state) {
    state.user.name = ''
    state.user.authenticated = false
    state.teams = []
    state.boards = []
  },
  addTeam (state, team) {
    state.teams.push(team)
  },
  addBoard (state, board) {
    state.boards.push(board)
  }
}

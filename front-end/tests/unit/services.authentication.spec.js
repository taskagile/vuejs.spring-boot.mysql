import moxios from 'moxios'
import authenticationService from '@/services/authentication'

describe('services/authentication', () => {
  beforeEach(() => {
    moxios.install()
  })

  afterEach(() => {
    moxios.uninstall()
  })

  it('should call `/authentications` API', () => {
    expect.assertions(1)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request.url).toEqual('/authentications')
      request.respondWith({
        status: 200,
        response: {result: 'success'}
      })
    })
    return authenticationService.authenticate()
  })

  it('should pass the response to caller when request succeeded', () => {
    expect.assertions(2)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request).toBeTruthy()
      request.respondWith({
        status: 200,
        response: {result: 'success'}
      })
    })
    return authenticationService.authenticate().then(data => {
      expect(data.result).toEqual('success')
    })
  })

  it('should propagate the error to caller when request failed', () => {
    expect.assertions(2)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request).toBeTruthy()
      request.reject({
        response: {
          status: 400,
          data: {message: 'Bad request'}
        }
      })
    })
    return authenticationService.authenticate().catch(error => {
      expect(error.message).toEqual('Bad request')
    })
  })
})

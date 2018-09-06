import { mount, createLocalVue } from '@vue/test-utils'
import RegisterPage from '@/views/RegisterPage'
import VueRouter from 'vue-router'
import Vuelidate from 'vuelidate'
import registrationService from '@/services/registration'
import { i18n } from '@/i18n'

// Adding Vue Router to the test so that
// we can access vm.$router
const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)
const router = new VueRouter()

// Mock dependency registrationService
jest.mock('@/services/registration')

describe('RegisterPage.vue', () => {
  let wrapper
  let fieldUsername
  let fieldEmailAddress
  let fieldFirstName
  let fieldLastName
  let fieldPassword
  let buttonSubmit
  let registerSpy

  beforeEach(() => {
    wrapper = mount(RegisterPage, {
      localVue,
      router,
      i18n
    })
    fieldUsername = wrapper.find('#username')
    fieldEmailAddress = wrapper.find('#emailAddress')
    fieldFirstName = wrapper.find('#firstName')
    fieldLastName = wrapper.find('#lastName')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type="submit"]')
    // Create spy for registration service
    registerSpy = jest.spyOn(registrationService, 'register')
  })

  afterEach(() => {
    registerSpy.mockReset()
    registerSpy.mockRestore()
  })

  afterAll(() => {
    jest.restoreAllMocks()
  })

  it('should render registration form', () => {
    expect(wrapper.find('.logo').attributes().src)
      .toEqual('/images/logo.png')
    expect(wrapper.find('.tagline').text())
      .toEqual('Open source task management tool')
    expect(fieldUsername.element.value).toEqual('')
    expect(fieldEmailAddress.element.value).toEqual('')
    expect(fieldFirstName.element.value).toEqual('')
    expect(fieldLastName.element.value).toEqual('')
    expect(fieldPassword.element.value).toEqual('')
    expect(buttonSubmit.text()).toEqual('Create account')
  })

  it('should contain data model with initial values', () => {
    expect(wrapper.vm.form.username).toEqual('')
    expect(wrapper.vm.form.emailAddress).toEqual('')
    expect(wrapper.vm.form.firstName).toEqual('')
    expect(wrapper.vm.form.lastName).toEqual('')
    expect(wrapper.vm.form.password).toEqual('')
  })

  it('should have form inputs bound with data model', () => {
    const username = 'sunny'
    const emailAddress = 'sunny@taskagile.com'
    const firstName = 'Sunny'
    const lastName = 'Hu'
    const password = 'VueJsRocks!'

    wrapper.vm.form.username = username
    wrapper.vm.form.emailAddress = emailAddress
    wrapper.vm.form.firstName = firstName
    wrapper.vm.form.lastName = lastName
    wrapper.vm.form.password = password
    expect(fieldUsername.element.value).toEqual(username)
    expect(fieldEmailAddress.element.value).toEqual(emailAddress)
    expect(fieldFirstName.element.value).toEqual(firstName)
    expect(fieldLastName.element.value).toEqual(lastName)
    expect(fieldPassword.element.value).toEqual(password)
  })

  it('should have form submit event handler `submitForm`', () => {
    const stub = jest.fn()
    wrapper.setMethods({submitForm: stub})
    buttonSubmit.trigger('submit')
    expect(stub).toBeCalled()
  })

  it('should register when it is a new user', async () => {
    expect.assertions(2)
    const stub = jest.fn()
    wrapper.vm.$router.push = stub
    wrapper.vm.form.username = 'sunny'
    wrapper.vm.form.emailAddress = 'sunny@taskagile.com'
    wrapper.vm.form.firstName = 'Sunny'
    wrapper.vm.form.lastName = 'Hu'
    wrapper.vm.form.password = 'JestRocks!'
    wrapper.vm.submitForm()
    expect(registerSpy).toBeCalled()
    await wrapper.vm.$nextTick()
    expect(stub).toHaveBeenCalledWith({name: 'login'})
  })

  it('should fail it is not a new user', async () => {
    expect.assertions(3)
    // In the mock, only sunny@taskagile.com is new user
    wrapper.vm.form.username = 'ted'
    wrapper.vm.form.emailAddress = 'ted@taskagile.com'
    wrapper.vm.form.firstName = 'Ted'
    wrapper.vm.form.lastName = 'Test'
    wrapper.vm.form.password = 'JestRocks!'
    expect(wrapper.find('.failed').isVisible()).toBe(false)
    wrapper.vm.submitForm()
    expect(registerSpy).toBeCalled()
    await wrapper.vm.$nextTick()
    expect(wrapper.find('.failed').isVisible()).toBe(true)
  })

  it('should fail when the email address is invalid', () => {
    wrapper.vm.form.username = 'test'
    wrapper.vm.form.emailAddress = 'bad-email-address'
    wrapper.vm.form.firstName = 'User'
    wrapper.vm.form.lastName = 'Test'
    wrapper.vm.form.password = 'JestRocks!'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })

  it('should fail when the username is invalid', () => {
    wrapper.vm.form.username = 'a'
    wrapper.vm.form.emailAddress = 'test@taskagile.com'
    wrapper.vm.form.firstName = 'User'
    wrapper.vm.form.lastName = 'Test'
    wrapper.vm.form.password = 'JestRocks!'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })

  it('should fail when the password is invalid', () => {
    wrapper.vm.form.username = 'test'
    wrapper.vm.form.emailAddress = 'test@taskagile.com'
    wrapper.vm.form.firstName = 'User'
    wrapper.vm.form.lastName = 'Test'
    wrapper.vm.form.password = 'bad!'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })


  it('should fail when the first name is invalid', () => {
    wrapper.vm.form.username = 'test'
    wrapper.vm.form.emailAddress = 'test@taskagile.com'
    wrapper.vm.form.firstName = ''
    wrapper.vm.form.lastName = 'Test'
    wrapper.vm.form.password = 'JestRocks!'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })


  it('should fail when the last name is invalid', () => {
    wrapper.vm.form.username = 'test'
    wrapper.vm.form.emailAddress = 'test@taskagile.com'
    wrapper.vm.form.firstName = 'User'
    wrapper.vm.form.lastName = ''
    wrapper.vm.form.password = 'JestRocks!'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })
})

import Vue from 'vue'
import RegisterPage from '@/views/RegisterPage'

describe('RegisterPage.vue', () => {
  it('should render correct contents', () => {
    const Constructor = Vue.extend(RegisterPage)
    const vm = new Constructor().$mount()
    expect(vm.$el.querySelector('.logo').getAttribute('src'))
      .toEqual('/static/images/logo.png')
    expect(vm.$el.querySelector('.tagline').textContent)
      .toEqual('Open source task management tool')
    expect(vm.$el.querySelector('#username').value).toEqual('')
    expect(vm.$el.querySelector('#emailAddress').value).toEqual('')
    expect(vm.$el.querySelector('#password').value).toEqual('')
    expect(vm.$el.querySelector('form button[type="submit"]').textContent)
      .toEqual('Create account')
  })
})

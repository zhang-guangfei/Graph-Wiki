import IO from 'socket.io-client'

export default {

  install(Vue, connection, opts) {
    let socket

    if (connection != null && typeof connection === 'object') { socket = connection } else { socket = IO(connection || '', opts) }

    Vue.prototype.$socket = socket

    const addListeners = function() {
      if (this.$options['socket']) {
        const conf = this.$options.socket
        if (conf.namespace) {
          this.$socket = IO(conf.namespace, conf.options)
        }

        if (conf.events) {
          const prefix = conf.prefix || ''
          Object.keys(conf.events).forEach((key) => {
            const func = conf.events[key].bind(this)
            this.$socket.on(prefix + key, func)
            conf.events[key].__binded = func
          })
        }
      }
    }

    const removeListeners = function() {
      if (this.$options['socket']) {
        const conf = this.$options.socket

        if (conf.namespace) {
          this.$socket.disconnect()
        }

        if (conf.events) {
          const prefix = conf.prefix || ''
          Object.keys(conf.events).forEach((key) => {
            this.$socket.off(prefix + key, conf.events[key].__binded)
          })
        }
      }
    }

    Vue.mixin({
      [Vue.version.indexOf('2') === 0 ? 'beforeCreate' : 'beforeCompile']: addListeners,
      beforeDestroy: removeListeners
    })
  }

}

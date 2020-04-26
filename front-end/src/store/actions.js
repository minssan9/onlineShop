import {account, order, item, qna, review, auth, cart, chat, mail} from '../api'

const actions = {

  LOGIN({commit, dispatch}, playload) {
    return auth.login(playload)
      .then(({data}) => {
        commit('LOGIN', data.access_token)
        dispatch('FETCH_ACCOUNT')
      })
  },

  // 사용자
  FETCH_ACCOUNT({commit}) {
    return account.fetch()
      .then(({data}) => {
        commit('SET_ACCOUNT', data)
        return data.status
      })
  },
  FETCH_MANAGER_ACCOUNT({commit}, playload) {
    return account.fetchManager(playload)
      .then(({data}) => {
        commit('SET_ACCOUNT_LIST', data)
      })
  },
  ADD_ACCOUNT(_, playload) {
    return account.create(playload)
      .then((data) => (data.status))
  },
  UPDATE_ACCOUNT({dispatch}, playload) {
    return account.put(playload)
      .then(() => {
        dispatch('FETCH_ACCOUNT')
      })
  },
  FETCH_MANAGER_ACCOUNT_SEARCH({commit}, playload) {
    return account.fetchManagerSearch(playload)
      .then(({data}) => {
        commit('SET_ACCOUNT_LIST', data)
      })
  },
  JOIN_ID_CHECK(_, playload) {
    return account.idCheck(playload)
      .then((data) => (data))
  },
  ADD_ACCOUNT_FILE({commit, dispatch}, playload) {
    return account.createFiles(playload)
      .then(({data}) => {
        commit('CLEAR_ACCOUNT_FILE')
      })
  },

  // cart
  FETCH_ACCOUNT_CART({commit}) {
    return cart.fetch()
      .then(({data}) => {
        commit('SET_CART_LIST', data)
      })
  },
  ADD_ACCOUNT_CART(_, playload) {
    return cart.create(playload)
      .then((data) => {
        alert('장바구니에 추가되었습니다.')
        return data.status
      })
  },
  DELETE_ACCOUNT_CART({dispatch}, playload) {
    return cart.destroy(playload)
      .then((data) => {
        dispatch('FETCH_ACCOUNT_CART')
        return data.status
      })
  },
  DELETE_ACCOUNT_CART_ALL({dispatch}) {
    return cart.destroyAll()
      .then((data) => {
        alert('전체 삭제 되었습니다.')
        dispatch('FETCH_ACCOUNT_CART')
        return data.status
      })
  },

  // 상품
  FETCH_ITEM({commit}, id) {
    return item.fetch(id)
      .then(({data}) => {
        commit('SET_ITEM', data)
      })
  },
  FETCH_ITEM_LIST({commit}, playload) {
    return item.fetchList(playload)
      .then(({data}) => {
        console.log(data)
        commit('SET_ITEM_LIST', data)
      })
  },
  FETCH_ITEM_LIST_SEARCH({commit}, playload) {
    return item.fetchSearch(playload)
      .then(({data}) => {
        commit('SET_ITEM_LIST', data)
      })
  },
  ADD_ITEM({commit}, playload) {
    return item.create(playload)
      .then(({data}) => {
      })
  },
  UPDATE_ITEM({dispatch}, playload) {
    return item.put(playload)
      .then(() => {
        dispatch('FETCH_ITEM', playload.id)
      })
  },
  DELETE_ITEM({dispatch}, playload) {
    return item.destroy(playload)
      .then(() => {
        dispatch('FETCH_ITEM_LIST', {page: 0})
      })
  },
  ADD_ITEM_FILE({dispatch}, playload) {
    return item.createFiles(playload.formData)
      .then(({data}) => {
        playload.item.itemFileList = data
        dispatch('ADD_ITEM', playload.item)
        dispatch('FETCH_ITEM_LIST', {page: 0})
      })
  },
  // 주문
  FETCH_ORDER_ACCOUNT_PAGE({commit}, id) {
    return order.fetchAccount(id)
      .then(({data}) => {
        commit('SET_ORDER_LIST', data)
      })
  },
  FETCH_ORDER_MANAGER_PAGE({commit}, id) {
    return order.fetchManager(id)
      .then(({data}) => {
        commit('SET_ORDER_LIST', data)
      })
  },
  ADD_ORDER({commit}, playload) {
    return order.create(playload)
      .then(() => {
        commit('CLEAR_BUY_LIST')
      })
  },
  UPDATE_ORDER({dispatch}, playload) {
    return order.put(playload)
      .then(() => {
        dispatch('FETCH_ORDER_MANAGER_PAGE', 0)
        // 임시로 0으로 해놨음
      })
  },
  FETCH_ORDER_MANAGER_SEARCH({commit}, playload) {
    return order.fetchManagerSearch(playload)
      .then(({data}) => {
        commit('SET_ORDER_LIST', data)
      })
  },
  FETCH_OPERATION_STATUS({commit}) {
    return order.fetchOperationStatus()
      .then(({data}) => {
        commit('SET_OPERATION_STATUS', data)
      })
  },
  FETCH_OPERATION_STATUS_SEARCH({commit}, playload) {
    return order.fetchOperationStatusSerach(playload)
      .then(({data}) => {
        commit('SET_OPERATION_STATUS', data)
      })
  },


  // 상품문의
  FETCH_QNA({commit}, playload) {
    return qna.fetch(playload)
      .then(({data}) => {
        commit('SET_QNA_LIST', data)
      })
  },
  FETCH_MANAGER_QNA({commit}, page) {
    return qna.fetchManager(page)
      .then(({data}) => {
        commit('SET_QNA_LIST', data)
      })
  },
  ADD_QNA({dispatch}, playload) {
    return qna.create(playload)
      .then(() => {
        dispatch('FETCH_ITEM', playload.itemId)
        dispatch('FETCH_QNA', {itemId: playload.itemId, page: 0})
      })
  },
  UPDATE_ANSWER({dispatch}, playload) {
    return qna.put(playload)
      .then(() => {
        alert('작성되었습니다.')
      })
  },
  DELETE_QNA({dispatch}, playload) {
    return qna.destroy(playload)
      .then(() => {
        dispatch('FETCH_MANAGER_QNA', {page: 0})
      })
  },
  FETCH_QNA_MANAGER_SEARCH({commit}, playload) {
    return qna.fetchManagerSearch(playload)
      .then(({data}) => {
        commit('SET_QNA_LIST', data)
      })
  },

  // 리뷰
  FETCH_REVIEW({commit}, playload) {
    return review.fetch(playload)
      .then(({data}) => {
        commit('SET_REVIEW_LIST', data)
      })
  },
  FETCH_MANAGER_REVIEW({commit}, playload) {
    return review.fetchManager(playload)
      .then(({data}) => {
        commit('SET_REVIEW_LIST', data)
      })
  },
  FETCH_REVIEW_MANAGER_SEARCH({commit}, playload) {
    return review.fetchManagerSearch(playload)
      .then(({data}) => {
        commit('SET_REVIEW_LIST', data)
      })
  },
  ADD_REVIEW({commit}, playload) {
    return review.create(playload)
      .then(({data}) => {
      })
  },
  ADD_REVIEW_FILE({dispatch}, playload) {
    return review.createFiles(playload.formData)
      .then(({data}) => {
        playload.review.reviewFile = data
        dispatch('ADD_REVIEW', {itemId: playload.item.id, review: playload.review})
      })
  },
  FETCH_MANAGER_CHAT({commit}, playload) {
    return chat.fetch(playload)
      .then(({data}) => {
        commit('SET_MESSAGE', data)
      })
  },
  FETCH_MANAGER_CHAT_LIST({commit}) {
    return chat.fetchList()
      .then(({data}) => {
        commit('SET_CHAT_LIST', data)
      })
  },
  READ({}, playload) {
    return chat.read(playload)
      .then(({data}) => {
      })
  },

  // 메일보내기

  SEND({}, playload) {
    return mail.mailSend(playload)
      .then(({data}) => {
      })
  }

}

export default actions

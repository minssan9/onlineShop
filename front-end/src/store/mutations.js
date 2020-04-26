import {setAuthInHeader} from '../api'

const mutations = {

  LOGIN(state, access_token) {
    if (!access_token) return
    state.access_token = access_token
    localStorage.access_token = access_token
    setAuthInHeader(access_token)
  },
  LOGOUT(state) {
    state.access_token = null
    state.account = null
    delete localStorage.access_token
    setAuthInHeader(null)
  },
  // 사용자
  SET_ACCOUNT(state, data) {
    state.account = []
    state.account = data
  },
  SET_ACCOUNT_LIST(state, data) {
    state.accountList = []
    state.accountList = data
  },
  SET_SEARCH_MODE(state, data) {
    state.search.mode = data.mode
    state.search.keyword = data.keyword
    state.search.option = data.option
    state.search.sort = data.sort
    state.search.page = data.page
  },
  CLEAR_SEARCH_MODE(state) {
    state.search.mode = false
    state.search.keyword = ''
    state.search.option = ''
    state.search.sort = ''
    state.search.page = ''
  },
  CLEAR_ACCOUNT_FILE(state) {
    state.account.accountFile = []
  },
  // 상품
  SET_ITEM(state, data) {
    state.item = []
    state.item = data
  },
  SET_ITEM_LIST(state, data) {
    state.itemList = []
    state.itemList = data
  },
  // 주문
  SET_ORDER_LIST(state, data) {
    state.orderList = []
    state.orderList = data
  },
  SET_CART_LIST(state, data) {
    state.cartList = []
    state.cartList = data
  },
  // 장바구니
  PUSH_BUY_LIST(state, data) {
    state.buyList.push(data)
  },
  REMOVE_BUY_LIST(state, data) {

    // 다삭제됨
    // state.buyList = state.buyList.filter(r => r.id !== data)

    for (let i = 0; i < state.buyList.length; i++) {
      if (state.buyList[i].id === data) {
        state.buyList.splice(i, 1)
        return
      }
    }
  },
  CLEAR_BUY_LIST(state) {
    state.buyList = []
  },

  CART_TO_BUYLIST(state) {
    state.cartList.forEach(r => {
      state.buyList.push(r.item)
    })
  },

  // 문의사항 리스트
  SET_QNA_LIST(state, data) {
    state.qnaList = []
    state.qnaList = data
  },

  // 리뷰 리스트
  SET_REVIEW_LIST(state, data) {
    state.reviewList = []
    state.reviewList = data
  },

  //운영현황
  SET_OPERATION_STATUS(state, data) {
    state.operationStatus = []
    state.operationStatus = data
  },
  SET_CHAT_LIST(state, data) {
    state.chatList = []
    state.chatList = data.content
  },
  SET_MESSAGE(state, data) {
    state.chat = []
    for (let i = 0; i < data.messages.length; i++)
      state.chat.push(data.messages[i]);
  },
  PUSH_MESSAGE(state, data) {
    state.chat.push(data)
  },
}

export default mutations

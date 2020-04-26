const state = {
  access_token: null,
  account: [],        // 로그인 사용자 정보
  accountList: [],
  cartList:[],
  item: [],           // 현재 상품 정보
  itemList: [],       // 상품목록 리스트
  orderList: [],      // 사용자가 주문했었던
  buyList: [],        // 사용자가 주문리스트에 담아둔
  qnaList: [],        // 관리자페이지의 문의사항 리스트
  reviewList: [],     //  리뷰리스트

  chat:[],
  chatList:[],
  chatRoomNumber:[],

  search:{
    mode:false,
    keyword:'',
    option:'',
    sort:'',
    page:''
  },

  operationStatus: ''

}

export default state

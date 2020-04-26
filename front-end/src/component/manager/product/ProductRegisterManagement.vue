<template>

  <div class="row mb-5">
    <Sidebar/>

    <div class="col-10">

      <div class="container">

        <div class="list-group-item mt-3 ">
          <p><strong>상품 기본정보</strong></p>
          <hr class="border-secondary">
          <div class="row">
            <strong>상품번호</strong>
            <input :disabled=true class="form-control" value="상품번호">

            <strong>상품명</strong>
            <input v-model="item.title" type="text" class="form-control" placeholder="상품명">
            <strong>설명</strong>
            <textarea v-model="item.content" class="form-control" rows="5"></textarea>

          </div>
        </div>

        <div class="list-group-item mt-3">
          <div class="row">
            <div class="col-sm-4">
              <strong>정가</strong>
              <input v-model="item.price" type="text" class="form-control" placeholder="정가">
            </div>
            <div class="col-sm-4">
              <strong>할인가</strong>
              <input v-model="item.discount" type="text" class="form-control" placeholder="할인가">
            </div>
            <div class="col-sm-4">
              <strong>배송예정일</strong>
              <input v-model="item.deliveryDate" type="text" class="form-control" placeholder="배송예정일">
            </div>
          </div>
        </div>

        <div class="list-group-item mt-3">
          <strong>옵션</strong>
          <div class="input-group">
            <input v-model="optionInput" class="form-control"/>
            <span class="input-group-btn">
               <button @click="onAddOption" class="btn btn-outline-secondary">추가</button>
            </span>
          </div>

          <ul class="list-group mt-2">
            <li v-for="(option,index) in item.options" :key="index" class="list-group-item">{{option.option}}</li>
          </ul>
        </div>

        <div class="list-group-item mt-3">

          <strong>파일업로드</strong>
          <ul class="list-group mt-2">
            <li v-for="file in files" class="list-group-item">{{file.name}}</li>
          </ul>
          <div class="custom-file mt-2">
            <input id="file" name="file" type="file" ref="files" class="custom-file-input" v-on:change="onFileList()"
                   multiple>
            <label class="custom-file-label" for="customFile">Choose file</label>
          </div>

        </div>

        <div class="text-right mt-3 mb-3">
          <button @click="showModal=true" class="btn btn-success">상품등록하기</button>
        </div>

        <Modal v-if="isRegister">
          <h3 slot="header">등록</h3>
          <div slot="body">
            정말 등록하시겠습니까?
          </div>
          <span slot="footer" @click="showModal=false">
      <button @click="onAddItem" class="btn btn-sm btn-warning">등록하기</button>
      <button class="btn btn-sm btn-warning">취소하기</button>
    </span>
        </Modal>

      </div>
    </div>
  </div>
</template>

<script>

  import {mapActions} from 'vuex'
  import Modal from '../../Modal'
  import Sidebar from '../Sidebar'

  export default {
    data() {
      return {
        item: {
          title: '',
          content: '',
          price: '',
          discount: '',
          options: [],
          deliveryDate: '',
          itemFileList: []
        },
        optionInput: '',
        files: '',
        showModal: false
      }
    },
    computed: {
      isRegister() {
        return this.showModal
      }
    },
    components: {
      Modal,
      Sidebar
    },
    methods: {
      ...mapActions([
        'ADD_ITEM_FILE'
      ]),
      onAddItem() {
        let formData = new FormData()
        let inputFile = document.querySelector('#file')
        let file = inputFile.files


        for (let i = 0; i < file.length; i++) {
          if (!this.isExtensionAndSize(file[i].name, file[i].size)) {
            return
          }
          formData.append('uploadFile', file[i])
        }

        // 파일업로드 -> 파일업로드결과 응답-> 아이템정보에 파일업로드결과 추가해서 다시요청

        this.ADD_ITEM_FILE({formData: formData, item: this.item})
        alert('등록이 완료되었습니다.')
        this.$router.push('/')
      },
      isExtensionAndSize(fileName, fileSize) {
        let regex = new RegExp('(.*?)\.(exe|sh|zip|alz)$')
        let maxSize = 1048576  // 1MB

        if (fileSize >= maxSize) {
          alert('파일 사이즈 초과')
          return false
        }

        if (regex.test(fileName)) {
          alert('해당 파일형식은 업로드 불가능합니다.')
          return false
        }

        return true
      },
      onFileList() {
        this.files = this.$refs.files.files;
      },
      onAddOption() {
        if (this.optionInput.length === 0) {
          alert("입력을 해주세요.")
          return
        }

        for (let i = 0; i < this.item.options.length; i++) {
          if (this.item.options[i].option === this.optionInput) {
            alert('이미 등록된 옵션입니다.')
            return
          }
        }

        this.item.options.push({option: this.optionInput})
        this.optionInput = ''
      }
    }
  }
</script>

<style scoped>

</style>

<template>
  <div style="padding: 20px;">
    <!-- 搜索区域 -->
    <div style="margin-bottom: 20px;">
      <el-input
        v-model="searchName"
        style="width:240px; margin-right: 10px;"
        placeholder="请输入书籍名称"
        clearable
        @keyup.enter.native="handleSearch">
      </el-input>
      <el-input
        v-model="searchAuthor"
        style="width:240px; margin-right: 10px;"
        placeholder="请输入作者"
        clearable
        @keyup.enter.native="handleSearch">
      </el-input>
      <el-button
        style="margin-right: 10px;"
        type="primary"
        @click="handleSearch">
        <i class="el-icon-search"></i> 搜索
      </el-button>
      <el-button
        @click="handleReset">
        <i class="el-icon-refresh"></i> 重置
      </el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="tableData" stripe v-loading="loading">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="bookName" label="书籍名称"></el-table-column>
      <el-table-column prop="author" label="作者" width="150"></el-table-column>
      <el-table-column prop="bookType" label="书籍分类" width="120"></el-table-column>
      <el-table-column prop="publishingHouse" label="出版社" width="180"></el-table-column>
      <el-table-column label="操作" width="120" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleBorrow(scope.row)">借阅</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="margin-top: 20px; text-align: right;">
      <el-pagination
          background
          @current-change="handlePageChange"
          :current-page="pageNum"
          :page-size="5"
          layout="total, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import request from '@/request.js'
export default {
  name:'AboutView',
  data() {
    return {
      tableData: [],
      searchName: '',
      searchAuthor: '',
      pageNum: 1,
      total: 0,
      loading: false
    }
  },
  created() {
    this.load()
  },
  methods: {
    async load() {
      this.loading = true
      try {
        const params = {
          pageNum: this.pageNum
        }
        if (this.searchName) params.bookName = this.searchName
        if (this.searchAuthor) params.author = this.searchAuthor

        const res = await request.get('/book/getBook', { params })
        if (res.code === '200') {
          // 修复：适配后端新的分页返回格式
          this.tableData = res.data.list || []
          this.total = res.data.total || 0
        } else {
          this.$message.error(res.msg || '获取图书列表失败')
        }
      } catch (error) {
        this.$message.error('网络请求失败')
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    handleSearch() {
      this.pageNum = 1
      this.load()
    },

    handleReset() {
      this.searchName = ''
      this.searchAuthor = ''
      this.pageNum = 1
      this.load()
    },

    handlePageChange(page) {
      this.pageNum = page
      this.load()
    },
    
    handleBorrow(row) {
      const user = JSON.parse(localStorage.getItem('user'))
      if (!user || !user.id) {
        this.$message.warning('请先登录')
        this.$router.push('/login')
        return
      }
      this.$confirm(`确定要借阅《${row.bookName}》吗？`, '提示', {
        confirmButtonText: '确定借阅',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        request.post('/borrow/add', { userId: user.id, bookId: row.id }).then(res => {
          if (res.code === '200') {
            this.$message.success('借阅成功，可在“我的借阅”查看')
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    }
  }
}
</script>

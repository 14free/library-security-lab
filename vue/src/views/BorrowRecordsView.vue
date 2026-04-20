<template>
  <div class="borrow-records">
    <h2>我的借阅记录</h2>
    
    <!-- 借阅记录列表 -->
    <el-table :data="records" style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="订单ID" width="100"></el-table-column>
      <el-table-column prop="bookId" label="图书ID" width="100"></el-table-column>
      <el-table-column prop="bookName" label="图书名称" min-width="150"></el-table-column>
      <el-table-column prop="borrowTime" label="借阅时间" width="180">
        <template slot-scope="scope">
          {{ formatDate(scope.row.borrowTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="returnTime" label="归还时间" width="180">
        <template slot-scope="scope">
          {{ scope.row.returnTime ? formatDate(scope.row.returnTime) : '未归还' }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="mini" @click="viewDetail(scope.row.id)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 空状态提示 -->
    <el-empty v-if="!loading && records.length === 0" description="暂无借阅记录"></el-empty>
  </div>
</template>

<script>
import request from '@/request.js'

export default {
  name: 'BorrowRecordsView',
  data() {
    return {
      records: [],
      loading: false
    }
  },
  created() {
    this.fetchRecords()
  },
  methods: {
    // 获取借阅记录列表
    async fetchRecords() {
      this.loading = true
      try {
        const user = JSON.parse(localStorage.getItem('user'))
        if (!user || !user.id) {
          this.$message.error('请先登录')
          this.$router.push('/login')
          return
        }
        
        const res = await request.get(`/borrow/records?userId=${user.id}`)
        if (res.code === '200') {
          this.records = res.data || []
        } else {
          this.$message.error(res.msg || '获取借阅记录失败')
        }
      } catch (error) {
        this.$message.error('网络请求失败')
        console.error(error)
      } finally {
        this.loading = false
      }
    },
    
    // 查看详情
    viewDetail(id) {
      this.$router.push(`/borrow-record/${id}`)
    },
    
    // 格式化日期
    formatDate(date) {
      if (!date) return ''
      const d = new Date(date)
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`
    },
    
    // 获取状态标签类型
    getStatusType(status) {
      const typeMap = {
        '借阅中': 'warning',
        '已归还': 'success',
        '逾期': 'danger'
      }
      return typeMap[status] || 'info'
    }
  }
}
</script>

<style scoped>
.borrow-records {
  padding: 20px;
}
h2 {
  margin-bottom: 20px;
}
</style>
<template>
  <div class="borrow-detail">
    <h2>借阅记录详情</h2>
    
    <el-card v-loading="loading" class="detail-card">
      <el-descriptions title="订单信息" :column="1" border>
        <el-descriptions-item label="订单ID">{{ record.id }}</el-descriptions-item>
        <el-descriptions-item label="用户ID">{{ record.userId }}</el-descriptions-item>
        <el-descriptions-item label="图书ID">{{ record.bookId }}</el-descriptions-item>
        <el-descriptions-item label="借阅时间">{{ formatDate(record.borrowTime) }}</el-descriptions-item>
        <el-descriptions-item label="归还时间">
          {{ record.returnTime ? formatDate(record.returnTime) : '未归还' }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusType(record.status)">
            {{ record.status }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(record.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ formatDate(record.updateTime) }}</el-descriptions-item>
      </el-descriptions>
      
      <div class="actions">
        <el-button @click="goBack">返回列表</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import request from '@/request.js'

export default {
  name: 'BorrowRecordDetailView',
  data() {
    return {
      record: {},
      loading: false
    }
  },
  created() {
    this.fetchDetail()
  },
  methods: {
    // 获取借阅记录详情
    async fetchDetail() {
      this.loading = true
      try {
        const id = this.$route.params.id
        const res = await request.get(`/borrow/record/${id}`)
        if (res.code === '200') {
          this.record = res.data || {}
        } else {
          this.$message.error(res.msg || '获取详情失败')
        }
      } catch (error) {
        this.$message.error('网络请求失败')
        console.error(error)
      } finally {
        this.loading = false
      }
    },
    
    // 返回列表
    goBack() {
      this.$router.back()
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
.borrow-detail {
  padding: 20px;
}
h2 {
  margin-bottom: 20px;
}
.detail-card {
  max-width: 800px;
}
.actions {
  margin-top: 20px;
  text-align: right;
}
</style>
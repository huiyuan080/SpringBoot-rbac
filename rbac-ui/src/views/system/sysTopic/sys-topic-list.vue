<template>
  <div>
    <el-card class="search-form">
      <el-form :inline="true" :model="page.params" size="small">
        <el-form-item label="主题">
          <el-input v-model="page.params.title" clearable placeholder="主题"/>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="page.params.author" clearable placeholder="作者"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="doSearch">查询</el-button>
          <el-button type="warning" icon="el-icon-refresh" @click="page.params = {}">重置</el-button>
          <el-button type="success" icon="el-icon-plus" @click="toAdd">添加</el-button>
        </el-form-item>
      </el-form>
    </el-card>


    <el-table border :data="dataPage.list" stripe style="width: 100%;margin-top: 3px">
      <el-table-column align="center" prop="topicId" label="序号"/>
      <el-table-column align="center" prop="title" label="主题"/>
      <el-table-column align="center" prop="content" label="内容" width="350px"/>
      <el-table-column align="center" prop="author" label="作者"/>
      <el-table-column align="center" prop="createDate" label="发布时间"></el-table-column>
      <el-table-column align="center" prop="clickAmount" label="点击量"></el-table-column>

      <el-table-column align="center" label="操作" fixed="right">
        <template slot-scope="{row}">
          <el-dropdown class="handle-button">
              <span class="el-dropdown-link">
                <el-link icon="el-icon-s-operation" type="primary">操作<i class="el-icon-arrow-down el-icon--right"/>
                </el-link>
              </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-button type="text" icon="el-icon-document" @click="doDetails(row)">详情</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button type="text" icon="el-icon-delete" @click="doDelete(row)">删除</el-button>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="dataPage.page"
      :page-sizes="[ 10,20,30,40]"
      :page-size="dataPage.page"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>

    <el-dialog :visible.sync="addShow" v-if="addShow" :title="title" center>
      <sys-topic-add @after="doSearch" @close="showdown"></sys-topic-add>
    </el-dialog>
    <el-dialog :visible.sync="detailsShow" v-if="detailsShow" :title="title" center>
      <sys-topic-details :active-obj="activeObj"@after="doSearch"></sys-topic-details>
    </el-dialog>

  </div>
</template>

<script>
import sysTopicApi from "@/api/system/sysTopic"
import SysTopicAdd from './sys-topic-add'
import SysTopicDetails from './sys-topic-details'

export default {
  components: {
    SysTopicAdd,
    SysTopicDetails
  },
  created() {
    this.doSearch()
  },
  name: "sys-topic-list",
  data() {
    return {
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {
          title: undefined,
          author: undefined,
        }
      },
      dataPage: [],
      addShow: false,
      detailsShow: false,
      title: undefined,
      activeObj: undefined,
      total: undefined,

    }
  },

  methods: {
    doSearch() {
      sysTopicApi.getByPage(this.page).then(res => {

        this.dataPage = res.data
        this.total = res.data.totalCount
      })
    },
    toAdd() {
      this.addShow = true
      this.title = "发布帖子"
    },
    doDelete(row) {
      this.$confirm("确定删除【" + row.title + "】这条记录吗?", {
        type: "warning"
      }).then(() => {
        sysTopicApi.delete(row.topicId).then(res => {
          this.$message.success(res.msg)
          this.doSearch()
        })
      })
    },
    showdown() {
      this.addShow = false
      this.detailsShow = false
    },
    doDetails(row) {
      this.activeObj = row
      this.title = "详情页面"
      this.detailsShow = true
    },
    handleSizeChange(val) {
      this.page.pageSize = val
      this.doSearch()
    },
    handleCurrentChange(val) {
      this.page.pageNumber = val;
      this.doSearch()
    }

  }


};

</script>

<style scoped>

</style>

<template>
  <div>
    <el-card>
      <el-form label-width="80px" :inline="true" style="margin-top: 5px">
        <el-form-item label="字典名称" size="mini">
          <el-input size="mini" placeholder="请输入字典名称" v-model="page.params.dictName"/>
        </el-form-item>
        <el-form-item label="状态" size="mini">
          <el-select value="" placeholder="请选择状态" size="mini" v-model="page.params.status">
            <el-option :value="0" label="启用"></el-option>
            <el-option :value="1" label="停用"></el-option>
          </el-select>
        </el-form-item>
        <el-button size="mini" type="primary" icon="el-icon-search"
                   style="margin-left: 20px" @click="doSearch">查 询
        </el-button>
        <el-button size="mini" type="info" icon="el-icon-refresh"
                   style="margin-left: 15px" @click="doReset">重 置
        </el-button>
        <el-button size="mini" type="success" icon="el-icon-plus"
                   style="margin-left: 15px" @click="doAdd">添 加
        </el-button>
        <el-button size="mini" type="success" icon="el-icon-refresh"
                   style="margin-left: 15px" @click="doCache">同步缓存
        </el-button>
      </el-form>

      <el-table :data="dictTypeList">
        <el-table-column label="类型编号 " align="center" prop="dictId"></el-table-column>
        <el-table-column label="字典名称 " align="center" prop="dictName"></el-table-column>
        <el-table-column label="字典类型 " align="center" prop="dictType">
          <template slot-scope="{row}">
            <!--编程式导航  跳转路由-->
            <router-link :to="'/system/dictData/index/'+row.dictType">{{ row.dictType }}</router-link>
          </template>
        </el-table-column>
        <el-table-column label="创建人 " align="center" prop="createBy"></el-table-column>
        <el-table-column label="创建时间 " align="center" prop="createTime"></el-table-column>
        <el-table-column label="备注 " align="center" prop="remark"></el-table-column>
        <el-table-column label="状态 " align="center" prop="status">
          <template slot-scope="{row}">
            <el-tag v-if="row.status === 0" size="mini" type="success">启用</el-tag>
            <el-tag v-if="row.status === 1" size="mini" type="danger">停用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作 " align="center">
          <template slot-scope="{row}">
            <el-dropdown>
              <span class="el-dropdown-link">
                <el-link type="primary">操作</el-link> <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-edit" @click="doUpdate(row.dictId)">修 改</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-delete" @click="doDelete(row)">删 除</el-button>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="dictTypeList.page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size=dictTypeList.pageSize
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>

    <!--添加字典信息-->

    <el-dialog :visible.sync="addShow" v-if="addShow" style="width: 75%;" :title="title" center>
      <sys-dict-type-add @after="doSearch" @showdown="closeDialog"></sys-dict-type-add>
    </el-dialog>


    <!--修改字典信息-->

    <el-dialog :visible.sync="updateShow" style="width: 75%;" v-if="updateShow" title="修改字典类型" center>
      <sys-dict-type-update :active-id="activeId" @after="doSearch" @showdown="closeDialog"></sys-dict-type-update>
    </el-dialog>

  </div>
</template>


<script>
import sysDictTypeApi from '@/api/sysDictType/sysDictType'
import SysDictTypeAdd from "./sys-dict-type-add"
import SysDictTypeUpdate from "./sys-dict-type-update"

export default {
  name: "sys-dict-type-list",
  components: {
    SysDictTypeAdd,
    SysDictTypeUpdate
  },

  data() {
    return {
      addShow: false,
      updateShow: false,
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {
          dictName: undefined,
          status: undefined,
        }
      },
      total: undefined,
      dictTypeList: [],
      activeId: undefined,
      title: undefined

    }
  },
  created() {
    this.doSearch()
  },
  methods: {

    doCache(){
      this.$message.success("同步成功")
    },


    doSearch() {
      sysDictTypeApi.Search(this.page).then(res => {
        this.dictTypeList = res.data.list
        this.total = res.data.totalCount
      })
    },
    doReset() {
      this.page = {
        pageNumber: 1,
        pageSize: 10,
        params: {
          dictName: undefined,
          status: undefined,
        }
      }
    },
    doAdd() {
      this.addShow = true
      this.title = "添加数据"

    },
    doUpdate(row) {
      this.activeId = row
      this.updateShow = true
    },
    closeDialog() {
      this.addShow = false
      this.updateShow = false
    },
    doDelete(row) {
      this.$confirm("确定要删除【" + row.dictName + "】这条信息吗？", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        sysDictTypeApi.delete(row.dictId).then(res => {
          this.$message.success(res.msg)
          this.doSearch()
        })
      })

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
}
</script>

<style scoped>

</style>

<template>
  <div>
    <el-row :gutter="5">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <el-card>
          <sys-uer-left @reloadData="doSearch"/>
        </el-card>
      </el-col>

      <el-card class="search-form">
        <el-form :inline="true" :model="page.params" size="small">
          <el-form-item label="用户名">
            <el-input v-model="page.params.userName" clearable placeholder="请输入用户名"/>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="page.params.nickName" clearable placeholder="请输入昵称"/>
          </el-form-item>
          <el-form-item label="状态">
            <el-select value="" v-model="page.params.status">
              <el-option value="0" label="正常"></el-option>
              <el-option value="1" label="停用"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="doSearch(null)">查询</el-button>
            <el-button type="warning" icon="el-icon-refresh" @click="doReset">重置</el-button>
            <el-button type="success" icon="el-icon-plus" @click="doAdd">添加</el-button>
          </el-form-item>
        </el-form>

        <el-table border :data="dataPage.list" stripe style="width: 100%;margin-top: 15px">
          <!--            <el-table-column align="center" prop="userId" label="编号"/>-->
          <el-table-column align="center" prop="userName" label="账号"/>
          <el-table-column align="center" prop="nickName" label="昵称"/>
          <el-table-column align="center" prop="email" label="邮箱" width="180px"/>
          <el-table-column align="center" prop="phone" label="电话" width="150px"/>
          <el-table-column align="center" prop="sex" label="性别">
            <template slot-scope="{row}">
              <el-tag v-if="row.sex === 0" size="mini" type="success">男</el-tag>
              <el-tag v-else-if="row.sex === 1" size="mini" type="danger">女</el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="header" label="所在部门" width="150px">
            <template slot-scope="{row}">
              <div v-for="dept in deptList" v-if="row.deptId===dept.deptId">{{ dept.deptName }}</div>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="status" label="状态">
            <template slot-scope="{row}">
              <el-tag v-if="row.status === 0" size="mini" type="success">启用</el-tag>
              <el-tag v-if="row.status === 1" size="mini" type="danger">停用</el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作" fixed="right">
            <template slot-scope="{row}">
              <el-dropdown class="handle-button">
              <span class="el-dropdown-link">
                <el-link icon="el-icon-s-operation" type="primary">操作<i class="el-icon-arrow-down el-icon--right"/>
                </el-link>
              </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>
                    <el-button type="text" icon="el-icon-document" @click="doInfo(row)">详情</el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button type="text" icon="el-icon-edit" @click="doUpdate(row)">修改</el-button>
                  </el-dropdown-item>
                  <el-dropdown-item v-if="row.userId!=1">
                    <el-button type="text" icon="el-icon-delete" @click="doDelete(row)">删除</el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button type="text" icon="el-icon-edit" @click="doRole(row)">分配角色</el-button>
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
      </el-card>
    </el-row>

    <el-dialog :visible.sync="addShow" v-if="addShow" title="添加页面" center>
      <sys-user-add @after="doSearch" @close="close"></sys-user-add>
    </el-dialog>
    <el-dialog :visible.sync="updateShow" v-if="updateShow" title="修改页面" center>
      <sys-user-update :active-obj="activeObj" @after="doSearch" @close="close"></sys-user-update>
    </el-dialog>

    <el-dialog :visible.sync="infoShow" v-if="infoShow" title="详情页面" center>
      <sys-user-info :active-obj="activeObj"></sys-user-info>
    </el-dialog>

    <el-dialog :visible.sync="roleShow" v-if="roleShow" title="分配权限页面" center>
      <sys-user-select-role :active-id="activeObj" @close="close"></sys-user-select-role>
    </el-dialog>
  </div>
</template>

<script>
import sysUserApi from "@/api/system/sysUser"
import sysDeptApi from "@/api/system/sysDept"
import SysUerLeft from './sys-user-left'
import SysUserAdd from './sys-user-add'
import SysUserUpdate from './sys-user-update'
import SysUserInfo from './sys-user-info'
import SysUserSelectRole from './sys-user-select-role'
import sysMenuApi from '@/api/system/sysMenu'

export default {
  components: {
    SysUerLeft,
    SysUserAdd,
    SysUserUpdate,
    SysUserInfo,
    SysUserSelectRole
  },
  created() {
    this.doSearch()
    this.loadAllDeptList()
    sysMenuApi.getRouters().then(res=>{
      console.log(res)
    })
  },
  name: "sys-user-list",
  data() {
    return {
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {
          userName: undefined,
          nickName: undefined,
          status: undefined,
          deptId: undefined
        }
      },
      dataPage: [],
      deptList: [],
      total: undefined,
      addShow: false,
      updateShow: false,
      activeObj: undefined,
      infoShow: false,
      roleShow: false
    }
  },

  methods: {
    doSearch(deptId) {
      if (deptId != null && deptId != undefined) {
        this.page.params.deptId = deptId;
      }
      sysUserApi.getByPage(this.page).then(res => {
        this.total = res.data.totalCount
        this.dataPage = res.data;
      })
    },
    doAdd() {
      this.addShow = true
    },
    doReset() {
      this.page = {
        pageNumber: 1,
        pageSize: 10,
        params: {
          userName: undefined,
          nickName: undefined,
          status: undefined,
          deptId: undefined
        }
      }
    },
    doUpdate(row) {
      this.updateShow = true
      this.activeObj = row
    },
    doDelete(row) {
      this.$confirm("确定删除这" + row.userName + "条信息吗?", {
        type: "warning"
      }).then(() => {
        sysUserApi.delete(row.userId).then(res => {
          this.$message.success(res.msg)
          this.doSearch()
        })
      })

    },
    doInfo(row) {
      this.infoShow = true
      this.activeObj = row
    },
    doRole(row) {
      this.roleShow = true
      this.activeObj = row.userId
    },


    loadAllDeptList() {
      sysDeptApi.listDept().then(res => {
        this.deptList = res.data
      })
    },
    close() {
      this.addShow = false
      this.updateShow = false
      this.roleShow = false
    },

    handleSizeChange(val) {
      this.page.pageSize = val
      this.doSearch()
    },
    handleCurrentChange(val) {
      this.page.pageNumber = val;
      this.doSearch()
    }
  },


}
</script>

<style scoped>

</style>

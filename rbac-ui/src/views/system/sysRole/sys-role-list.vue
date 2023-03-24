<template>
  <div>
    <el-card>
      <el-form label-width="80px" :inline="true" style="margin-top: 5px">
        <el-form-item label="字典名称" size="mini">
          <el-input size="mini" placeholder="请输入字典名称" v-model="page.params.roleName"/>
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
      </el-form>


      <el-table :data="roleList">
        <el-table-column label="角色编码 " align="center" prop="roleId"></el-table-column>
        <el-table-column label="角色名称 " align="center" prop="roleName"></el-table-column>
        <el-table-column label="角色顺序 " align="center" prop="roleSort"></el-table-column>
        <el-table-column label="备注 " align="center" prop="remark"></el-table-column>
        <el-table-column label="状态 " align="center" prop="status">
          <template slot-scope="{row}">
            <el-tag v-if="row.status === 0" size="mini" type="success">启用</el-tag>
            <el-tag v-if="row.status === 1" size="mini" type="danger">停用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建人 " align="center" prop="createBy"></el-table-column>
        <el-table-column label="创建时间 " align="center" prop="createTime"></el-table-column>
        <el-table-column label="操作 " align="center">
          <template slot-scope="{row}">
            <el-dropdown>
              <span class="el-dropdown-link">
                <el-link type="primary">操作</el-link> <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-edit" @click="doUpdate(row)">修 改</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-delete" @click="doDelete(row)">删 除</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-delete" @click="doRoleSelectMenus(row)">角色菜单分配</el-button>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="roleList.page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size=roleList.pageSize
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>

    <el-dialog :visible.sync="addShow" v-if="addShow" title="添加角色" center>
      <sys-role-add @after="doSearch" @close="showdown"></sys-role-add>
    </el-dialog>
    <el-dialog :visible.sync="updateShow" v-if="updateShow" title="修改角色" center>
      <sys-role-update :active-obj="activeObj" @after="doSearch" @close="showdown"></sys-role-update>
    </el-dialog>
    <el-dialog :visible.sync="selectShow" v-if="selectShow" title="分配权限" center>
      <sys-role-select-menu-pro :active-obj="activeObj" @after="doSearch" @close="showdown"></sys-role-select-menu-pro>
    </el-dialog>
  </div>
</template>

<script>
import sysDictData from "@/api/sysDictData/sysDictData";
import sysRoleApi from "@/api/system/sysRole"
import SysRoleAdd from './sys-role-add'
import SysRoleUpdate from './sys-role-update'
// import SysRoleSelectMenu from './sys-role-select-menu'
import SysRoleSelectMenuPro from './sys-role-select-menu-pro'



export default {
  name: "sys-role-list",
  components: {
    SysRoleSelectMenuPro,
    SysRoleAdd,
    SysRoleUpdate,
    // SysRoleSelectMenu
  },
  created() {
    this.doSearch()
    this.loadDictDataByDictType()
  },
  data() {
    return {
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {
          dictName: undefined,
          status: undefined,
        }
      },
      statusOptions: [],
      roleList: [],
      total: undefined,
      addShow: false,
      updateShow: false,
      selectShow: false,
      activeObj: undefined
    }
  },

  methods: {
    doAdd() {
      this.addShow = true
    },
    doUpdate(row) {
      this.updateShow = true
      this.activeObj = row
    },
    doDelete(row) {
      this.$confirm("确定要删除【" + row.roleName + "】这条数据吗？", {
        type: "warning"
      }).then(() => {
        sysRoleApi.delete(row.roleId).then(() => {
          this.$message.success("删除成功")
          this.doSearch()
        })
      })
    },
    doRoleSelectMenus(row) {
      this.selectShow = true
      this.activeObj = row.roleId
    },
    doSearch() {
      sysRoleApi.getByPage(this.page).then(res => {
        this.total = res.data.totalCount
        this.roleList = res.data.list
      })
    },
    doReset() {
      this.page = {
        pageNumber: 1,
        pageSize: 10,
        params: {
          roleName: undefined,
          status: undefined,
        }
      }
    },
    showdown() {
      this.addShow = false
      this.updateShow = false
      this.selectShow = false
    },
    loadDictDataByDictType() {
      sysDictData.getDictDataByDictType("sys_status_type").then(res => {
        this.statusOptions = res.data
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

<template>

  <div>
    <el-card>
      <el-form label-width="80px" :inline="true" size="mini">
        <el-form-item label="部门名称">
          <el-input v-model="queryParams.deptName"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select value="" v-model="queryParams.status">
            <el-option :value="0" label="启用"></el-option>
            <el-option :value="1" label="停用"></el-option>
          </el-select>
        </el-form-item>
        <el-button size="mini" type="primary" icon="el-icon-search" style="margin-left: 15px"
                   @click="loadAllDeptList">搜索
        </el-button>
        <el-button size="mini" type="info" icon="el-icon-refresh" style="margin-left: 15px"
                   @click="doReset"
        >重置
        </el-button>
      </el-form>
    </el-card>


    <!--
          row-key:唯一标识
          default-expand-all:表格是否默认展开
          :tree-props : 对表格的数据进行约束 描述表格数据的格式
      -->
    <el-card style="margin-top: 5px">
      <el-button type="primary" icon="el-icon-plus" @click="doAdd">新增</el-button>
      <el-button type="info" icon="el-icon-sort" @click="doExpandAll">展开/折叠</el-button>
      <el-table
        style="margin-top: 5px"
        :data="deptList"
        row-key="deptId"
        border
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        v-if="refreshTable">
        <el-table-column
          prop="deptName"
          align="center"
          label="部门名称">
        </el-table-column>
        <el-table-column
          prop="orderNum"
          align="center"
          label="排序">
        </el-table-column>
        <el-table-column
          prop="leader"
          align="center"
          label="负责人">
        </el-table-column>
        <el-table-column
          prop="phone"
          align="center"
          label="负责人电话">
        </el-table-column>
        <el-table-column
          prop="email"
          align="center"
          label="邮箱">
        </el-table-column>
        <el-table-column
          prop="status"
          align="center"
          label="状态">
          <template slot-scope="{row}">
            <el-tag v-if="row.status === 0" size="mini" type="success">启用</el-tag>
            <el-tag v-if="row.status === 1" size="mini" type="danger">停用</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center">
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
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :visible.sync="addShow" v-if="addShow" title="添加部门信息" center>
      <sys-dept-add @after="loadAllDeptList" @showdown="closeShow"></sys-dept-add>
    </el-dialog>

    <el-dialog :visible.sync="updateShow" v-if="updateShow" title="修改部门信息" center>
      <sys-dept-update :active-id="activeId" @after="loadAllDeptList" @showdown="closeShow"></sys-dept-update>
    </el-dialog>
  </div>
</template>

<script>
import sysDeptApi from "@/api/system/sysDept"
import sysDictData from "@/api/sysDictData/sysDictData";
import {handleTree} from "@/utils/powernode";
import SysDeptAdd from './sys-dept-add'
import SysDeptUpdate from './sys-dept-update'

export default {
  created() {
    this.loadDictDataByDictType()
    this.loadAllDeptList()
  },
  components: {
    SysDeptAdd,
    SysDeptUpdate
  },
  name: "sys-dept-list",
  data() {
    return {
      activeId:undefined,
      deptList: [],
      queryParams: {
        deptName: undefined,
        status: undefined
      },
      statusOptions: [],
      addShow: false,
      updateShow:false,
      isExpandAll: true,
      refreshTable: true
    }
  },
  methods: {
    doExpandAll() {
      //清楚原始dom
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      //如果发生变化  重新渲染dom
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    loadDictDataByDictType() {
      sysDictData.getDictDataByDictType("sys_user_sex").then(res => {
        this.statusOptions = res.data
      })
    },
    loadAllDeptList() {
      sysDeptApi.listDept(this.queryParams).then(res => {
        // console.log(res)
        //转换数据格式
        this.deptList = handleTree(res.data, "deptId")
      })
    },
    doAdd() {
      this.addShow = true;
    },
    doReset() {
      this.queryParams = {}
    },
    doUpdate(row){
       this.updateShow=true;
       this.activeId = row.deptId
      // console.log(row.deptId)
    },
    doDelete(row) {
      this.$confirm("确定删除【" + row.deptName + "】这行数据吗？").then(() => {
        sysDeptApi.delete(row.deptId).then(res => {
          this.$message.success(res.msg)
          this.loadAllDeptList()
        })
      })
    },
    closeShow() {
      this.addShow = false
      this.updateShow=false
    }


  }
}
</script>

<style scoped>

</style>

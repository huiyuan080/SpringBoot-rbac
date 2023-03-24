<template>
  <div>
    <el-card>
      <el-form label-width="80px" :inline="true" size="mini">
        <el-form-item label="菜单名称">
          <el-input v-model="queryParams.menuName"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select value="" v-model="queryParams.status">
            <el-option :value="0" label="启用"></el-option>
            <el-option :value="1" label="停用"></el-option>
          </el-select>
        </el-form-item>
        <el-button size="mini" type="primary" icon="el-icon-search" style="margin-left: 15px"
                   @click="doSearch">搜索
        </el-button>
        <el-button size="mini" type="info" icon="el-icon-refresh" style="margin-left: 15px"
                   @click="doReset">重置
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
        :data="menuList"
        row-key="menuName"
        border
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        v-if="refreshTable">
        <el-table-column
          prop="menuName"
          align="center"
          label="菜单名称">
        </el-table-column>
        <el-table-column
          prop="icon"
          align="center"
          label="图标">
          <template slot-scope="{row}">
            <svg-icon :icon-class="row.icon"></svg-icon>
          </template>
        </el-table-column>
        <el-table-column
          prop="orderNum"
          align="center"
          label="排序">
        </el-table-column>
        <el-table-column
          prop="perms"
          align="center"
          label="权限标识 "
          :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column
          prop="component"
          align="center"
          label="组件路径"
          :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column
          prop="path"
          align="center"
          label="路由地址"
          :show-overflow-tooltip="true">
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
          prop="menuType"
          align="center"
          label="类型">
          <template slot-scope="{row}">
            <el-tag :type="row.menuType===0?'success':row.menuType===1?'info':'warning'">
              {{ row.menuType === 0 ? '目录' : row.menuType === 1 ? '菜单' : '按钮' }}
            </el-tag>
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
                <el-dropdown-item v-if="row.menuType!==2">
                  <el-button type="text" icon="el-icon-plus" @click="doAddLine(row)">添 加</el-button>
                </el-dropdown-item>
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


    <el-dialog :visible.sync="addShow" v-if="addShow" title="添加菜单" center>
      <sys-menu-add :active-obj="activeObj" @after="loadAllDeptList" @close="showdown"></sys-menu-add>
    </el-dialog>
    <el-dialog :visible.sync="updateShow" v-if="updateShow" title="修改菜单" center>
      <sys-menu-update :active-obj="activeObj" @after="loadAllDeptList" @close="showdown"></sys-menu-update>
    </el-dialog>

  </div>
</template>

<script>

import sysMenuApi from "@/api/system/sysMenu";
import {handleTree} from "@/utils/powernode";
import sysDictData from "@/api/sysDictData/sysDictData";
import SvgIcon from "@/components/SvgIcon/index"
import SysMenuAdd from "@/views/system/sysMenu/sys-menu-add";
import SysMenuUpdate from './sys-menu-update'

export default {
  name: "sys-menu-list",
  components: {
    SysMenuAdd,
    SysMenuUpdate,
    SvgIcon
  },
  created() {
    this.loadAllDeptList()
    this.loadDictDataByDictType()
  },
  data() {
    return {
      queryParams: {
        menuName: undefined,
        status: undefined,
      },
      menuList: [],
      //控制是否默认设置展开
      isExpandAll: false,
      refreshTable: true,
      statusOptions: [],
      addShow: false,
      updateShow: false,
      activeObj: undefined

    }
  },
  methods: {
    doAdd() {
      this.addShow = true
    },
    doSearch() {
      sysMenuApi.listMenu(this.queryParams).then(res => {
        this.menuList = handleTree(res.data, "menuId")
      })
    },
    doReset() {
      this.queryParams = {}
    },
    doExpandAll() {
      //清楚原始dom
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      //如果发生变化  重新渲染dom
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    //加载数据
    loadAllDeptList() {
      sysMenuApi.listMenu(this.queryParams).then(res => {
        console.log(res)
        //转换数据格式
        this.menuList = handleTree(res.data, "menuId")
      })
    },
    loadDictDataByDictType() {
      sysDictData.getDictDataByDictType("sys_user_sex").then(res => {
        this.statusOptions = res.data
      })
    },
    showdown() {
      this.addShow = false
      this.updateShow = false
    },

    doAddLine(row) {
      this.addShow = true;
      this.activeObj = row
    },
    doUpdate(row){
      this.activeObj=row
      this.updateShow=true
    },
    doDelete(row) {
      this.$confirm("确定要删除【" + row.menuName + "】这条数据吗?", {
        type: "warning"
      }).then(() => {
        sysMenuApi.delete(row.menuId).then(res => {
          if (res.code === 20000){
            this.loadAllDeptList()
            this.$message.success(res.msg)
          }else {
            this.loadAllDeptList()
            this.$message.info(res.msg)
          }

        })
      })

    }
  }
}
</script>

<style scoped>

</style>

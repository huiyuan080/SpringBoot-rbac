<template>
  <div>
    <!--
       default-expand-all 控制树  是否默认展开
       node-key  唯一键
     -->
    <el-tree
      :data="menuList"
      show-checkbox
      node-key="menuId"
      ref="tree"
      :props="defaultProps"
      :check-on-click-node="true"
      @node-click="getMenuIds">
    </el-tree>

      <div align="center">
        <el-button type="primary" icon="el-icon-plus" size="small"
                   style="margin-top: 15px" @click="doAddMenus">添加权限
        </el-button>
      </div>

  </div>
</template>

<script>
import sysMenuApi from "@/api/system/sysMenu"
import {handleTree} from "@/utils/powernode";
import sysRole from "@/api/system/sysRole";

export default {
  props: {
    activeObj: {
      default: undefined,
      type: Number
    },
  },
  watch: {
    activeObj: {
      immediate: true,
      handler: function (newVal, oldVal) {
        console.log(this.activeObj)

      }
    }
  },
  created() {
    this.loadAllMenuList()
  },
  name: "sys-role-select-menu",
  data() {
    return {
      menuList: [],
      defaultProps: {
        children: 'children',
        label: 'menuName'
      },
      menuIds: []
    }
  },

  methods: {
    doAddMenus() {

      sysRole.saveRoleMenu(this.activeObj, this.menuIds).then(res => {
        this.$message.success(res.msg);
        this.$emit("close");
        this.$emit("after");
      })
    },
    getMenuIds() {
      //获取选中的菜单id
      this.menuIds = this.$refs.tree.getCheckedKeys();
      //获取半选中的菜单id ...会把数组里的数据进行拆分  依次放入到数组里
      this.menuIds.push(...this.$refs.tree.getHalfCheckedKeys())
      console.log(this.menuIds)

    },

    loadAllMenuList() {
      sysMenuApi.listMenu().then(res => {
        this.menuList = handleTree(res.data, "menuId")
      })
    },
  }
}
</script>

<style scoped>

</style>

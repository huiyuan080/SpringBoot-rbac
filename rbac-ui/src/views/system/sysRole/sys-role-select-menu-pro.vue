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
      :props="defaultProps">
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
        sysRole.getCurrentRoleHasMenuIdsByRoleId(this.activeObj).then(res=>{
          //方法回调 把已经选中的复选框显示出来
          this.$refs.tree.setCheckedKeys(res.data,true)
        })

      }
    }
  },
  created() {
    this.loadAllMenuList()
  },
  name: "sys-role-select-menu-pro",
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
      //获取选中的菜单id
      this.menuIds = this.$refs.tree.getCheckedKeys();
      //获取半选中的菜单id ...会把数组里的数据进行拆分  依次放入到数组里
      this.menuIds.push(...this.$refs.tree.getHalfCheckedKeys())
      sysRole.saveRoleMenu(this.activeObj, this.menuIds).then(res => {
        this.$message.success(res.msg);
        this.$emit("close");
        this.$emit("after");
      })
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

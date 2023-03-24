<template>
  <div>
    <el-form label-width="100px" :model="form" ref="form" :rules="rules" size="mini">
      <el-form-item label="上级菜单" prop="parentId">
        <TreeSelect v-model="form.parentId" :show-count="true" :normalizer="normalizer" :options="menuList"
                    placeholder="上级菜单"></TreeSelect>
      </el-form-item>

      <el-form-item label="菜单类型" prop="icon">
        <el-radio-group v-model="form.menuType">
          <el-radio :label="0">目录</el-radio>
          <el-radio :label="1">菜单</el-radio>
          <el-radio :label="2">按钮</el-radio>
        </el-radio-group>
      </el-form-item>

      <!--      <el-form-item label="菜单图标" prop="icon" v-if="form.menuType!==2">
              <el-input v-model="form.icon" placeholder="请选择菜单图标"></el-input>
            </el-form-item>-->
      <el-row>
      <el-col :span="24" v-if="form.menuType !== 2">
        <el-form-item label="菜单图标" prop="icon">
          <el-popover
            placement="bottom-start"
            width="460"
            trigger="click"
            @show="$refs['iconSelect'].reset()">
            <IconSelect ref="iconSelect" @selected="selected"/>
            <el-input slot="reference" v-model="form.icon" placeholder="点击选择图标" readonly>
              <svg-icon
                v-if="form.icon"
                slot="prefix"
                :icon-class="form.icon"
                class="el-input__icon"
                style="height: 32px;width: 16px;"
              />
              <i v-else slot="prefix" class="el-icon-search el-input__icon"/>
            </el-input>
          </el-popover>
        </el-form-item>
      </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="菜单名称" prop="menuName">
            <el-input v-model="form.menuName" placeholder="请输入菜单名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="form.orderNum" controls-position="right"
                             :min="1" :max="10"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="路由地址" prop="path" v-if="form.menuType!==2">
             <span slot="label">
                <el-tooltip content="访问的路由地址，如：`user`，如外网地址需内链访问则以`http(s)://`开头" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                路由地址
              </span>
            <el-input v-model="form.path" placeholder="请输入路由地址"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="组件路径" prop="component" v-if="form.menuType===1">
            <span slot="label">
            <el-tooltip content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
            组件路径
            </span>
            <el-input v-model="form.component" placeholder="请输入组件路径"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="权限字符" prop="perms" v-if="form.menuType===2">
            <span slot="label">
            <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasPermi('system:user:list')`)" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
            权限字符
            </span>
            <el-input v-model="form.perms" placeholder="请输入权限字符"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="路由参数" prop="query" v-if="form.menuType===1">
            <span slot="label">
                <el-tooltip content='访问路由的默认传递参数，如：`{"id": 1, "name": "ry"}`' placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                路由参数
              </span>
            <el-input v-model="form.query" placeholder="请输入路由参数"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="显示状态" v-if="form.menuType!==2">
             <span slot="label">
                <el-tooltip content="选择隐藏则路由将不会出现在侧边栏，但仍然可以访问" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                显示状态
              </span>

            <el-radio-group v-model="form.status">
              <el-radio v-for="dictData in statusOptions" :label="dictData.dictValue">{{ dictData.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="菜单状态" v-if="form.menuType!==2">
             <span slot="label">
                <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                菜单状态
              </span>

            <el-radio-group v-model="form.visible">
              <el-radio v-for="dictData in visibleOptions" :label="dictData.dictValue">{{ dictData.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-button type="success" align="center" size="small" @click="doAdd">添加</el-button>
      <el-button type="info" align="center" size="small" @click="doReset">重置</el-button>

    </el-form>
  </div>
</template>

<script>
import TreeSelect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import {handleTree} from "@/utils/powernode";
import sysMenuApi from "@/api/system/sysMenu"
import sysDictData from "@/api/sysDictData/sysDictData";
import IconSelect from "@/components/IconSelect";

export default {
  name: "sys-menu-add",
  props: {
    activeObj: {
      default: undefined,
      type: Object
    }
  },
  watch: {
    activeObj: {
      immediate: true,
      handler: function (newVal, oldVal) {
        console.log(this.activeObj.menuType)
        this.form.menuType = this.activeObj.menuType
        this.form.parentId = this.activeObj.parentId

      }
    }
  },

  components: {
    TreeSelect,
    IconSelect
  },
  created() {
    this.getMenuMC()
    sysDictData.getDictDataByDictType("sys_status_type").then(res => {
      this.statusOptions = res.data
    })
    sysDictData.getDictDataByDictType("sys_show_hide").then(res => {
      this.visibleOptions = res.data
    })
  },
  data() {
    return {
      form: {
        parentId: 0,
        visible: 0,
        status: 0,
        menuType: 0,
        icon: "404"
      },
      menuList: [],
      statusOptions: [],
      visibleOptions: [],
      rules: {
        menuName: [{required: true, trigger: "blur", message: "菜单名称不能为空"}],
        orderNum: [{required: true, trigger: "blur", message: "请选择排序"}],
        path: [{required: true, trigger: "blur", message: "请输入路由地址"}],
      }
    }


  },
  methods: {
    getMenuMC() {
      sysMenuApi.listMenuMC().then(res => {
        let list = handleTree(res.data, "menuId");
        let topMenu = [{
          "menuId": 0,
          "menuName": "主类目",
          children: []
        }];
        topMenu[0].children = list;
        this.menuList = topMenu
      })
    },
    // 选择图标
    selected(name) {
      this.form.icon = name;
    },

    doAdd() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          sysMenuApi.save(this.form).then(res => {
            this.$emit("after")
            this.$emit("close")
            this.$message.success("添加成功")

          })
        }
      })
    },
    doReset() {
      this.form = {
        visible: 0,
        status: 0,
        menuType: 0
      }
    },

    normalizer(node) {
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children,
      }
    },
  }
}
</script>

<style scoped>

</style>

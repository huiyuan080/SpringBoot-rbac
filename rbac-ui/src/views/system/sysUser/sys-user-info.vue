<template>
  <div>
    <div class="header-container">
      <el-image
        style="width: 100px; height: 100px"
        :src="sysUser.avatar"
        fit="fill"
      />
    </div>

    <el-descriptions class="margin-top" title="详情查看" :column="3"  border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          用户名
        </template>
        {{sysUser.userName}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          手机号
        </template>
        {{sysUser.phone}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label" >
          <i class="el-icon-location-outline"></i>
          用户类型
        </template>
        <el-tag :type="sysUser.userType==0?chaoji:putong">{{ sysUser.userType==0?"超级用户":"系统用户" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          性别
        </template>
        <el-tag size="small">学校</el-tag>
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          昵称
        </template>
        {{ sysUser.nickName }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          邮箱
        </template>
        {{ sysUser.email }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          创建人
        </template>
        {{ sysUser.createBy }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          创建时间
        </template>
        {{ sysUser.createTime }}
      </el-descriptions-item>

    </el-descriptions>
  </div>
</template>

<script>
export default {
  name: "sys-user-info",
  props:{
    activeObj:{
      type:Object,
      default:undefined
    }
  },
  watch:{
    activeObj:{
      immediate:true,
      handler:function (newVal, oldVal) {
        console.log(newVal)
        this.sysUser = newVal
      }
    }
  },
  data(){
    return{
      sysUser:[],
      chaoji:"success",
      putong:"info"
    }
  },
  methods:{


  },


}
</script>

<style scoped>
.header-container {
  text-align: center;
  margin-bottom: 15px;
}

</style>

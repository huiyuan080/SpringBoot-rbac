<template>
  <div>
    <el-card>
      <el-form :inline="true" size="mini">
        <el-form-item label="标签">
          <el-input placeholder="请输入标签" v-model="page.params.dictLabel"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select value="" v-model="page.params.status">
            <el-option value="0" label="正常"></el-option>
            <el-option value="1" label="停用"></el-option>
          </el-select>
        </el-form-item>


        <el-button type="primary" size="mini" icon="el-icon-search" style="margin-left: 15px" @click="doSearch">查询
        </el-button>
        <el-button type="info" size="mini" icon="el-icon-refresh" style="margin-left: 15px" @click="doReset">重置
        </el-button>
        <el-button type="success" size="mini" icon="el-icon-plus" style="margin-left: 15px" @click="doAdd">添加
        </el-button>

      </el-form>


      <el-table style="margin-top: 15px" :data="dictDataList">
        <el-table-column label="字典编码" prop="dictCode" align="center"></el-table-column>
        <el-table-column label="字典标签" prop="dictLabel" align="center"></el-table-column>
        <el-table-column label="字典键值" prop="dictValue" align="center"></el-table-column>
        <el-table-column label="字典排序" prop="dictSort" align="center"></el-table-column>
        <el-table-column label="字典类型" prop="dictType" align="center"></el-table-column>
        <el-table-column label="字典状态" prop="status" align="center">
          <template slot-scope="{row}">
            <el-tag v-if="row.status === 0" size="mini" type="success">启用</el-tag>
            <el-tag v-if="row.status === 1" size="mini" type="danger">停用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="字典备注" prop="remark" align="center"></el-table-column>
        <el-table-column label="操作">
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

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="dictDataList.page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size=dictDataList.pageSize
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>

    <!--
      添加字典数据信息
    -->
    <el-dialog :visible.sync="addShow" v-if="addShow" style="width: 75%;" title="添加字典数据信息" center>
      <sys-dict-data-add :dict-type-son="this.page.params.dictType" @after="doSearch" @showdown="closeDialog"></sys-dict-data-add>
    </el-dialog>


<!--
      修改字典信息
    -->
    <el-dialog :visible.sync="updateShow" style="width: 75%;" v-if="updateShow" title="修改字典类型" center>
      <sys-dict-data-update :dict-code-son="this.dictCodeSon" @after="doSearch" @showdown="closeDialog"></sys-dict-data-update>
    </el-dialog>

  </div>
</template>

<script>
import sysDictDataApi from "@/api/sysDictData/sysDictData"
import SysDictDataAdd from './sys-dict-data-add'
import SysDictDataUpdate from './sys-dict-data-update'

export default {
  name: "sys-dict-data-list",
  components: {
    SysDictDataAdd,
    SysDictDataUpdate
  },
  created() {
    this.page.params.dictType = this.$route.params.dictType
    this.doSearch()
  },
  data() {
    return {
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {
          dictType: undefined,
          dictLabel: undefined,
          status: undefined
        }
      },
      total: undefined,
      dictDataList: [],
      addShow: false,
      updateShow:false,
      dictTypeSon: undefined,
      dictCodeSon:undefined


    }
  },

  methods: {
    doSearch() {
      sysDictDataApi.search(this.page).then(res => {
        // console.log(res)
        this.dictTypeSon=res.data.list.dictType
        this.total = res.data.totalCount
        this.dictDataList = res.data.list
      })
    },
    doReset() {
      this.page = {
        pageNumber: 1,
        pageSize: 10,
        params: {
          dictType: this.$route.params.dictType,
          dictLabel: undefined,
          status: undefined
        }
      }
    },
    doAdd() {
      this.addShow = true
    },
    doUpdate(row){
      this.updateShow=true
      this.dictCodeSon = row.dictCode
      console.log(this.dictCodeSon)
    },
    doDelete(row){
      console.log(row)
      sysDictDataApi.delete(row.dictCode).then(res=>{
        this.$message.success(res.msg)
        this.doSearch()
      })
    },

    closeDialog(){
      this.addShow = false
      this.updateShow=false
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

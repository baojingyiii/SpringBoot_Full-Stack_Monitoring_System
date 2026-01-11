
import http from "@/http";

//获取cpu负载数据
const getCpuLoadApi = () =>{
    return http.get("/metrics/cpuload")
}

//导出
export {getCpuLoadApi}
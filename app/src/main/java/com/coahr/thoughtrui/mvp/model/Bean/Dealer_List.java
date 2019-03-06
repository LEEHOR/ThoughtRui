package com.coahr.thoughtrui.mvp.model.Bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/5
 * 描述：
 */
public class Dealer_List {

    /**
     * dealerList : [{"sale_code":"2340112","name":"武汉友芝友","areaAddress":"湖北省武汉市汉阳区","service_code":"FB2","id":"01aa76e75b8f4dd3bb8512110c27818b"},{"sale_code":"2340112","name":"武汉友芝友","areaAddress":"湖北省武汉市汉阳区","service_code":"FB2","id":"24cf603b2bf74e0ca2a0d87834d0f612"},{"sale_code":"A56","name":"华晨雷诺金杯武汉金色港湾店","areaAddress":"湖北省武汉市蔡甸区","service_code":"FB2","id":"54aa23bf6cad49e6923137ba7c0b0c05"},{"sale_code":"A20","name":"华晨雷诺金杯武汉中北路店","areaAddress":"湖北省武汉市武昌区","service_code":"FB2","id":"55b59294f93e4665b41ed73bd2f6cca7"},{"sale_code":"123","name":"东风本田东合店","areaAddress":"湖北省武汉市蔡甸区","service_code":"FB2","id":"641e75bc2dbf48a0a001e5f18edf922e"},{"sale_code":"0231154","name":"梅德赛斯奔驰朝阳公园店","areaAddress":"北京市北京市朝阳区","service_code":"FB2","id":"70f0559bdb044e8bbe2fd663f40ba982"},{"sale_code":"A18","name":"华晨雷诺金杯武汉武广店","areaAddress":"湖北省武汉市江汉区","service_code":"FB2","id":"74d3eb41f7f846fd8a68da07b6e02991"},{"sale_code":"A20","name":"华晨雷诺金杯硚口店","areaAddress":"湖北省武汉市硚口区","service_code":"FB2","id":"76f65f17d2c94e7fbca484fcc96f4b69"},{"sale_code":"A12","name":"华晨雷诺金杯武汉四新店","areaAddress":"湖北省武汉市汉阳区","service_code":"FB2","id":"77f047bfc1734b2abac32f2e63e9f3ea"},{"sale_code":"C56","name":"华晨雷诺金杯江汉大学店","areaAddress":"湖北省武汉市蔡甸区","service_code":"FB2","id":"7d72cc6339844c54a008be459e22a73c"},{"sale_code":"2340112","name":"武汉友芝友","areaAddress":"湖北省武汉市汉阳区","service_code":"FB2","id":"80ae7ead635e406b9d4d6fc019d4a5bf"},{"sale_code":"1234","name":"上汽别克沌口店","areaAddress":"湖北省武汉市蔡甸区","service_code":"FB2","id":"94eb68a045004600a3da5e2fcb1621c2"},{"sale_code":"1111111","name":"一汽奥迪武汉客厅店","areaAddress":"湖北省武汉市东西湖区","service_code":"FB2","id":"9952cc55fec14d3b922d7e1d96ba3497"},{"sale_code":"C60","name":"华晨雷诺金杯东合中心店","areaAddress":"湖北省武汉市蔡甸区","service_code":"FB2","id":"9d9c1a1ed13e4dfa98266e8e67165a76"},{"sale_code":"2222222222","name":"2233333","areaAddress":"安徽省芜湖市繁昌县","service_code":"FB2","id":"a111ee0b78f7425084b642175eac20ea"},{"sale_code":"A33","name":"华晨雷诺金杯武汉后湖店","areaAddress":"湖北省武汉市江岸区","service_code":"FB2","id":"ab8db5dbe92042b1a86e29e3289c85f5"},{"sale_code":"11.43002","name":"武汉仁和胜利车业有限公司","areaAddress":"湖北省武汉市江岸区","service_code":"FB2","id":"aca1cfd2b5514f62a576153f16c08802"},{"sale_code":"A23","name":"华晨雷诺武汉钟家村店","areaAddress":"湖北省武汉市汉阳区","service_code":"FB2","id":"c0efaf3895bc4cb4869f7cf81aa452a6"},{"sale_code":"A16","name":"华晨雷诺金杯武汉中山公园店","areaAddress":"湖北省武汉市江汉区","service_code":"FB2","id":"e92009cbf8e140e49bf27fb94a780ddd"},{"sale_code":"A123456","name":"华晨雷诺金杯武汉东合中心","areaAddress":"湖北省武汉市蔡甸区","service_code":"FB2","id":"ee32169595ba4b7d9dd8800f1f3528d1"},{"sale_code":"A05","name":"华晨雷诺武汉武广店","areaAddress":"湖北省武汉市江汉区","service_code":"FB2","id":"f3856702c6b842c69da8a08424de0138"},{"sale_code":"A11","name":"华晨雷诺金杯武汉王家湾店","areaAddress":"湖北省武汉市汉阳区","service_code":"FB2","id":"f5f37e35494b4d42ad5bf53a0ab11dc9"}]
     * status : 1
     */

    private int status;
    private List<DealerListBean> dealerList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DealerListBean> getDealerList() {
        return dealerList;
    }

    public void setDealerList(List<DealerListBean> dealerList) {
        this.dealerList = dealerList;
    }

    public static class DealerListBean implements IPickerViewData {
        /**
         * sale_code : 2340112
         * name : 武汉友芝友
         * areaAddress : 湖北省武汉市汉阳区
         * service_code : FB2
         * id : 01aa76e75b8f4dd3bb8512110c27818b
         */

        private String sale_code;
        private String name;
        private String areaAddress;
        private String service_code;
        private String id;

        public String getSale_code() {
            return sale_code;
        }

        public void setSale_code(String sale_code) {
            this.sale_code = sale_code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAreaAddress() {
            return areaAddress;
        }

        public void setAreaAddress(String areaAddress) {
            this.areaAddress = areaAddress;
        }

        public String getService_code() {
            return service_code;
        }

        public void setService_code(String service_code) {
            this.service_code = service_code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String getPickerViewText() {
            return this.name;
        }
    }
}

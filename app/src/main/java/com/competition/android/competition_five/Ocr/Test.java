package com.competition.android.competition_five.Ocr;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hasee on 2017/8/11.
 */

public class Test {

    /**
     * log_id : 469276162
     * direction : 0
     * words_result_num : 3
     * words_result : [{"chars":[{"char":"姓","location":{"width":26,"top":132,"height":32,"left":46}},{"char":"名","location":{"width":26,"top":132,"height":32,"left":98}},{"char":":","location":{"width":16,"top":132,"height":32,"left":133}},{"char":"崔","location":{"width":27,"top":132,"height":33,"left":171}},{"char":"哲","location":{"width":26,"top":133,"height":32,"left":210}}],"location":{"width":212,"top":128,"height":41,"left":35},"words":"姓名:崔哲","vertexes_location":[{"y":128,"x":35},{"y":128,"x":246},{"y":167,"x":246},{"y":167,"x":35}]},{"chars":[{"char":"学","location":{"width":31,"top":207,"height":38,"left":27}},{"char":"号","location":{"width":30,"top":207,"height":38,"left":82}},{"char":":","location":{"width":19,"top":207,"height":38,"left":124}},{"char":"1","location":{"width":18,"top":209,"height":36,"left":161}},{"char":"5","location":{"width":18,"top":209,"height":36,"left":182}},{"char":"0","location":{"width":15,"top":213,"height":31,"left":205}},{"char":"7","location":{"width":16,"top":213,"height":31,"left":234}},{"char":"0","location":{"width":16,"top":213,"height":31,"left":254}},{"char":"9","location":{"width":16,"top":213,"height":31,"left":273}},{"char":"4","location":{"width":15,"top":213,"height":31,"left":294}},{"char":"2","location":{"width":16,"top":213,"height":31,"left":323}},{"char":"4","location":{"width":16,"top":213,"height":31,"left":343}},{"char":"9","location":{"width":15,"top":213,"height":31,"left":363}}],"location":{"width":363,"top":204,"height":44,"left":25},"words":"学号:1507094249","vertexes_location":[{"y":204,"x":25},{"y":204,"x":386},{"y":246,"x":386},{"y":246,"x":25}]},{"chars":[{"char":"院","location":{"width":34,"top":294,"height":41,"left":25}},{"char":"系","location":{"width":35,"top":294,"height":41,"left":77}},{"char":":","location":{"width":21,"top":294,"height":41,"left":120}},{"char":"计","location":{"width":34,"top":294,"height":41,"left":156}},{"char":"算","location":{"width":34,"top":294,"height":43,"left":211}},{"char":"机","location":{"width":34,"top":294,"height":43,"left":251}},{"char":"与","location":{"width":34,"top":294,"height":43,"left":306}},{"char":"控","location":{"width":34,"top":294,"height":43,"left":346}},{"char":"制","location":{"width":33,"top":294,"height":43,"left":389}},{"char":"工","location":{"width":31,"top":297,"height":40,"left":440}},{"char":"程","location":{"width":31,"top":297,"height":40,"left":479}},{"char":"学","location":{"width":32,"top":300,"height":39,"left":528}},{"char":"就","location":{"width":32,"top":300,"height":39,"left":579}}],"location":{"width":610,"top":286,"height":66,"left":13},"words":"院系:计算机与控制工程学就","vertexes_location":[{"y":286,"x":13},{"y":286,"x":622},{"y":350,"x":622},{"y":350,"x":13}]}]
     */

    private int log_id;
    private int direction;
    private int words_result_num;
    private List<WordsResultBean> words_result;

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * chars : [{"char":"姓","location":{"width":26,"top":132,"height":32,"left":46}},{"char":"名","location":{"width":26,"top":132,"height":32,"left":98}},{"char":":","location":{"width":16,"top":132,"height":32,"left":133}},{"char":"崔","location":{"width":27,"top":132,"height":33,"left":171}},{"char":"哲","location":{"width":26,"top":133,"height":32,"left":210}}]
         * location : {"width":212,"top":128,"height":41,"left":35}
         * words : 姓名:崔哲
         * vertexes_location : [{"y":128,"x":35},{"y":128,"x":246},{"y":167,"x":246},{"y":167,"x":35}]
         */

        private LocationBean location;
        private  String words;
        private List<CharsBean> chars;
        private List<VertexesLocationBean> vertexes_location;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public List<CharsBean> getChars() {
            return chars;
        }

        public void setChars(List<CharsBean> chars) {
            this.chars = chars;
        }

        public List<VertexesLocationBean> getVertexes_location() {
            return vertexes_location;
        }

        public void setVertexes_location(List<VertexesLocationBean> vertexes_location) {
            this.vertexes_location = vertexes_location;
        }

        public static class LocationBean {
            /**
             * width : 212
             * top : 128
             * height : 41
             * left : 35
             */

            private int width;
            private int top;
            private int height;
            private int left;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }
        }

        public static class CharsBean {
            /**
             * char : 姓
             * location : {"width":26,"top":132,"height":32,"left":46}
             */

            @SerializedName("char")
            private String charX;
            private LocationBeanX location;

            public String getCharX() {
                return charX;
            }

            public void setCharX(String charX) {
                this.charX = charX;
            }

            public LocationBeanX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanX location) {
                this.location = location;
            }

            public static class LocationBeanX {
                /**
                 * width : 26
                 * top : 132
                 * height : 32
                 * left : 46
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class VertexesLocationBean {
            /**
             * y : 128
             * x : 35
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }
    }
}

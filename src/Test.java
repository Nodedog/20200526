

//通过开散列的方式来处理hash冲突
public class Test {
    static class Node{
        public int key;
        public int value;
        public Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }


    private static final double LOAD_FACTOR = 0.75;
    //array就是hash表的本体.数组的每一个元素又是一个链表的头结点
    private Node[] array = new Node[101];
    private int size = 0;//表示当前hash表中的元素个数

    private int hashFunc(int key){
        return key % array.length;
    }


    //插入
    public void put(int key,int value){
        //1.需要把key映射成数组下标
        int index = hashFunc(key);
        //2.根据下标找到对应的链表
        Node  list = array[index];
        //3.当前key在链表中是否存在
        for(Node cur = list; cur != null; cur = cur.next){
            if (cur.key == key){
                //key已经存在,直接修改value即可
                cur.value = value;
                return;
            }
        }
        //4.如果刚才循环结束,没有找到相同的key的节点,就直接插入到链表的头部
        Node newNode = new Node(key,value);
        newNode.next = list;
        array[index] = newNode;
        size++;
        if (size / array.length > LOAD_FACTOR){
            resize();
        }
        
    }

    //扩容
    private void resize() {
        Node[] newArray = new Node[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            for (Node cur = array[i]; cur != null; cur = cur.next){
                int index = cur.key % newArray.length;
                Node newNode = new Node(cur.key,cur.value);
                newNode.next = newArray[index];
                newArray[index] = newNode;
            }
        }
        array = newArray;
    }


    //查找
    //根据key查找指定元素.如果找到返回对应的value 如果没找到返回null
    public Integer get(int key){
        //1.先计算出key 对应的下标
        int index = hashFunc(key);
        //2.根据下标找到对应的链表
        Node list = array[index];
        //3.在链表中查找指定元素
        for(Node cur = list; cur != null; cur = cur.next){
            if (cur.key == key){
                return cur.value;
            }
        }
        return null;
    }




    //删除

}

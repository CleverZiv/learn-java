package com.leng.algorithm.tree;

/**
 * @Classname RBTree
 * @Date 2021/2/3 22:07
 * @Autor lengxuezhang
 * 1.创建RBTree，定义颜色
 * 2.创建RBNode
 * 3.辅助方法定义：parentOf(node), isRed(node), isBlack(node), setRed(node), setBlack(node), inOrderPrint()
 * 4.左旋方法定义：leftRotate(node)
 * 5.右旋方法定义：rightRotate(node)
 * 6.公开插入接口方法定义: insert(K key,V value)
 * 7.内部插入接口方法定义: insert(RBNode node)
 * 8.修正插入导致红黑树失衡的方法定义: insertFIxUp(RBNode node)
 * 9.测试红黑树正确性
 * 参考：https://www.jianshu.com/p/cc8befb774f6
 * B 站讲解：小刘四源码-https://www.bilibili.com/video/BV19K4y1x78v?from=search&seid=5453862202315976415
 * 个人印象笔记：https://app.yinxiang.com/fx/eb84beb9-4781-4769-bcf3-2d5e5e5597e9
 */
public class RBTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // 根节点的引用
    private RBNode root;

    public RBNode getRoot() {
        return root;
    }

    /**
     * 返回当前节点的父节点
     * @param node
     * @return
     */
    public RBNode getParent(RBNode node) {
        if(node != null) {
            return node.parent;
        }
        return null;
    }

    /**
     * 获取当前节点的父结点
     *
     * @param node
     */
    private RBNode parentOf(RBNode node) {
        if (node != null) {
            return node.parent;
        }
        return null;
    }

    /**
     * 节点是否为红色
     * @param node
     * @return
     */
    public boolean isRed(RBNode node) {
        if(node != null) {
            return node.color == RED;
        }
        return false;
    }
    /**
     * 设置节点为红色
     *
     * @param node
     */
    private void setRed(RBNode node) {
        if (node != null) {
            node.color = RED;
        }
    }

    /**
     * 设置节点为黑色
     *
     * @param node
     */
    private void setBlack(RBNode node) {
        if (node != null) {
            node.color = BLACK;
        }
    }

    /**
     * 节点是否为黑色
     *
     * @param node
     */
    private boolean isBlack(RBNode node) {
        if (node != null) {
            return node.color == BLACK;
        }
        return false;
    }

    public void inOrderPrint() {
        inOrderPrint(this.root);
    }

    /**
     * 中序遍历
     * @param node
     */
    private void inOrderPrint(RBNode node) {
        if(node != null) {
            inOrderPrint(node.left);
            System.out.println("key：" + node.key + ",value：" + node.value);
            inOrderPrint(node.right);
        }
    }

    public void insert (K key, V value) {
        RBNode node = new RBNode();
        node.setKey(key);
        node.setValue(value);
        // 新节点默认是红色的
        node.setColor(RED);
        insert(node);
    }

    /**
     * 内部插入方法
     * @param node
     */
    private void insert (RBNode node) {
        // 1. 先找到插入的位置，首先找到当前节点的父节点，利用二分法，从根节点开始往下找
        RBNode parent = null;
        RBNode x = root;
        while (x != null) {
            parent = x;
            // cmp > 0 说明node.key 大于 x.key 需要到x的右子树查找
            // cmp == 0 说明node.key 等于 x.key 说明需要进行替换操作
            // cmp < 0 说明node.key 小于 x.key 说明需要到x的左子树查找
            int cmp = node.key.compareTo(x.key);
            if(cmp > 0){
                x = x.right;
            }else if(cmp == 0) {
                // 位置上无需处理，值进行替换
                x.setValue(node.getValue());
                return;
            }else {
                x = x.left;
            }
        }
        node.parent = parent;
        if(parent != null) {
            // 2. 判断应该插入到父节点的左边还是右边
            int cmp = node.key.compareTo(parent.key);
            if(cmp > 0) {
                parent.right = node;
            }else {
                parent.left = node;
            }
        }else {
            // parent == null，证明新插入的节点没有父节点，也就是新插入的节点是根节点
            this.root = node;
        }
        // 3. 进行红黑树树的变换，来保证插入之后仍然符合红黑树
        insertFixUp(node);
    }

    private void insertFixUp(RBNode node) {
        // 根节点置为黑色
        this.root.setColor(BLACK);
        // 获取当前节点的父节点和爷爷节点
        RBNode parent = parentOf(node);
        RBNode gparent = parentOf(parent);
        /**
         * |---情景1：红黑树为空树，将根节点染色为黑色。
         * |---情景2：插入节点的key已经存在，不需要处理。
         * |---情景3：插入节点的父节点为黑色, 因为你所插入的路径, 黑色节点没有变化, 所以红黑树依然平衡, 所以不需要处理。
         */
        // 以上三个情景中，情景1第一行代码已处理，情景2在 insert 方法中已处理，情景3无需处理，所以主要来处理情景四：
        /**
         * |---情景4：插入的节点的父节点为红色
         *     |---情景4.1：叔叔节点存在，并且为红色。将爸爸和叔叔染色为黑色，将爷爷染色为红色，并且再以爷爷节点为当前节点，进行下一轮处理。
         *     |---情景4.2：叔叔节点不存在，或者为黑色，父节点为爷爷节点的左子树。
         *           |---情景4.2.1：插入节点为其父节点的左子节点（LL情况）, 将爸爸染色为黑色, 将爷爷染色为红色, 然后以爷爷节点右旋, 就完成了。
         *           |---情景4.2.2：插入节点为其父节点的右子节点（LR情况）, 以爸爸节点进行一次左旋, 得到LL双红的情景(4.2.1), 然后指定爸爸节点为当前节点进行下一轮处理
         *     |---情景4.3：叔叔节点不存在，或者为黑色，父节点为爷爷节点的右子树
         *           |---情景4.3.1：插入节点为其父节点的右子节点（RR情况）, 将爸爸染色为黑色, 将爷爷染色为红色, 然后以爷爷节点左旋, 就完成了。
         *           |---情景4.3.2：插入节点为其父节点的左子节点（RL情况）, 以爸爸节点进行一次右旋, 得到RR双红的情景(4.3.1), 然后指定爸爸节点为当前节点进行下一轮处理
         */
        if(parent != null && isRed(parent)){
            // parent != null 默认 gparent 一定存在
            RBNode uncle = null;
            if(parent == gparent.left) {
                uncle = gparent.right;
                // 情景4.1：叔叔节点存在，并且为红色。将爸爸和叔叔染色为黑色，将爷爷染色为红色，并且再以爷爷节点为当前节点，进行下一轮处理。
                if(uncle != null && isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    insertFixUp(gparent);
                    return;
                }
                // 情景4.2：叔叔节点不存在，或者为黑色，父节点为爷爷节点的左子树。
                if(uncle == null || isBlack(uncle)) {
                    // 情景4.2.2：插入节点为其父节点的右子节点（LR情况）, 以爸爸节点进行一次左旋, 得到LL双红的情景(4.2.1), 然后指定爸爸节点为当前节点进行下一轮处理
                    if(node == parent.right) {
                        leftRotate(parent);
                        insertFixUp(parent);
                        return;
                    }
                    // 情景4.2.1：插入节点为其父节点的左子节点（LL情况）, 将爸爸染色为黑色, 将爷爷染色为红色, 然后以爷爷节点右旋, 就完成了。
                    if(node == parent.left) {
                        setBlack(parent);
                        setRed(gparent);
                        rightRotate(gparent);
                        return;
                    }
                }
            } else {
                uncle = gparent.left;
                // 情景4.1：叔叔节点存在，并且为红色。将爸爸和叔叔染色为黑色，将爷爷染色为红色，并且再以爷爷节点为当前节点，进行下一轮处理。
                if(uncle != null && isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    insertFixUp(gparent);
                    return;
                }
                // 情景4.3：叔叔节点不存在，或者为黑色
                if(uncle == null || isBlack(uncle)) {
                    // 情景4.3.2：插入节点为其父节点的左子节点（RL情况）, 以爸爸节点进行一次右旋, 得到RR双红的情景(4.3.1), 然后指定爸爸节点为当前节点进行下一轮处理
                    if(node == parent.left) {
                        rightRotate(parent);
                        insertFixUp(parent);
                        return;
                    }
                    // 情景4.3.1：插入节点为其父节点的右子节点（RR情况）, 将爸爸染色为黑色, 将爷爷染色为红色, 然后以爷爷节点左旋, 就完成了。
                    if(node == parent.right) {
                        setBlack(parent);
                        setRed(gparent);
                        leftRotate(gparent);
                        return;
                    }

                }

            }

        }


    }

    /**
     * 左旋方法
     * 左旋示意图：左旋x节点
     *    p                   p
     *    |                   |
     *    x                   y
     *   / \         ---->   / \
     *  lx  y               x   ry
     *     / \             / \
     *    ly  ry          lx  ly
     *
     * 左旋做了几件事？
     * 1.将x的右子节点指向y的左子节点(ly), 将y的左子节点的父节点更新为x,
     * 2.当x的父节点(不为空时),更新y的父节点为x的父节点,并将x的父节点指定子树(当前x的子树位置)指定为y
     * 3.将x的父节点更为y,将y的左子节点更新为x
     */
    private void leftRotate(RBNode x) {
        RBNode y = x.right;
        RBNode ly = y.left;
        x.right = ly;
        if(ly != null) {
            ly.parent = x;
        }
        if(x.parent != null) {
            y.parent = x.parent;
            if(x == x.parent.left) {
                x.parent.left = y;
            }else{
                x.parent.right = y;
            }
        }else {
            // 说明x为根节点,此时需要更新y为根节点引用
            this.root = y;
            this.root.parent = null;
        }
        x.parent = y;
        y.left = x;

    }

    /**
     * 右旋方法
     * 右旋示意图：右旋y节点
     *
     *    p                       p
     *    |                       |
     *    y                       x
     *   / \          ---->      / \
     *  x   ry                  lx  y
     * / \                         / \
     *lx  ly                      ly  ry
     *
     * 右旋都做了几件事？
     * 1.将y的左子节点指向x的右子节点,并且更新x的右子节点的父节点为y
     * 2.当y的父节点不为空时,更新x的父节点为y的父节点,更新y的父节点的指定子节点(y当前的位置)为x
     * 3.更新y的父节点为x,x的右子节点为y
     */

    private void rightRotate(RBNode y) {
        RBNode x = y.left;
        RBNode ly = x.right;
        y.left = ly;
        if(ly != null) {
            ly.parent = y;
        }
        if(y.parent != null) {
            x.parent = y.parent;
            if(y == y.parent.left) {
                y.parent.left = x;
            }else{
                y.parent.right = x;
            }
        }else {
            this.root = x;
            this.root.parent = null;
        }
        x.right = y;
        y.parent = x;
    }

    /**
     * 静态内部类：定义节点
     * @param <K>
     * @param <V>
     */
    static class RBNode<K extends Comparable<K>, V> {
        private RBNode parent;
        private RBNode left;
        private RBNode right;
        private boolean color;
        private K key;
        private V value;

        public RBNode() {

        }

        public RBNode(RBNode parent, RBNode left, RBNode right, boolean color, K key, V value) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
            this.key = key;
            this.value = value;
        }
        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

    }

}

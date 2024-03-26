#include <iostream>
using namespace std;

template<class T>
struct Node {
    T data;
    Node<T>* left;
    Node<T>* right;

    Node(T item) {
        data = item;
        left = right = nullptr;
    }
};

template<class T>
class BinaryTree {
    Node<T>* root;

    Node<T>* insertRec(Node<T>* root, T data) {
        if (root == nullptr) {
            root = new Node<T>(data);
            return root;
        }

        if (data < root->data)
            root->left = insertRec(root->left, data);
        else if (data > root->data)
            root->right = insertRec(root->right, data);

        return root;
    }

    Node<T>* deleteRec(Node<T>* root, T data) {
        if (root == nullptr)
            return root;

        if (data < root->data)
            root->left = deleteRec(root->left, data);
        else if (data > root->data)
            root->right = deleteRec(root->right, data);
        else {
            if (root->left == nullptr) {
                Node<T>* temp = root->right;
                delete root;
                return temp;
            } else if (root->right == nullptr) {
                Node<T>* temp = root->left;
                delete root;
                return temp;
            }

            Node<T>* temp = minValue(root->right);
            root->data = temp->data;
            root->right = deleteRec(root->right, temp->data);
        }

        return root;
    }

    Node<T>* minValue(Node<T>* root) {
        T minv = root->data;
        while (root->left != nullptr) {
            minv = root->left->data;
            root = root->left;
        }
        return root;
    }

    Node<T>* searchRec(Node<T>* root, T data) {
        if (root == nullptr || root->data == data)
            return root;

        if (data < root->data)
            return searchRec(root->left, data);

        return searchRec(root->right, data);
    }

    void drawTree(Node<T>* root, int space) {
        const int COUNT = 5;
        if (root == nullptr)
            return;

        space += COUNT;

        drawTree(root->right, space);

        cout << endl;
        for (int i = COUNT; i < space; i++)
            cout << " ";
        cout << root->data << endl;

        drawTree(root->left, space);
    }

public:
    BinaryTree() {
        root = nullptr;
    }

    void insert(T data) {
        root = insertRec(root, data);
    }

    void deleteNode(T data) {
        root = deleteRec(root, data);
    }

    Node<T>* search(T data) {
        return searchRec(root, data);
    }

    void draw() {
        cout << "Binary Tree:" << endl;
        drawTree(root, 0);
    }
};

int main() {
    BinaryTree<int> intTree;
    intTree.insert(4);
    intTree.insert(2);
    intTree.insert(1);
    intTree.insert(3);
    intTree.insert(6);
    intTree.insert(5);
    intTree.insert(7);

    cout << "Search (4): " << intTree.search(4) << endl;
    cout << "Search (10): " << intTree.search(10) << endl;

    intTree.deleteNode(4);
    cout << "Search (4): " << intTree.search(4) << endl;

    intTree.draw();

    BinaryTree<double> doubleTree;
    doubleTree.insert(4.5);
    doubleTree.insert(2.3);
    doubleTree.insert(6.7);
    doubleTree.insert(1.2);
    doubleTree.insert(3.9);

    doubleTree.draw();

    BinaryTree<string> stringTree;
    stringTree.insert("apple");
    stringTree.insert("banana");
    stringTree.insert("orange");
    stringTree.insert("pear");

    stringTree.draw();

    return 0;
}

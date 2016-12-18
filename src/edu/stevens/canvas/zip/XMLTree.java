package edu.stevens.canvas.zip;

import java.io.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;

public class XMLTree extends JTree{   
    private           DefaultMutableTreeNode      treeNode;  //JTree的根节点
    private           DocumentBuilderFactory 	dbf; 
    // 这三个成员变量是xml parser需要的
    private           DocumentBuilder 		db; 
    private           Document              doc;
    public XMLTree() {
    	
    }
    public XMLTree(String fileName) throws ParserConfigurationException {
    	dbf = DocumentBuilderFactory.newInstance(); 
        //生成dbf的实例
        db = dbf.newDocumentBuilder();  
        //生成db的实例
        treeNode = createTreeNode( getXMLRoot( fileName ) );  
        //解析该xml文件，返回JTree的根节点
        setModel( new DefaultTreeModel( treeNode ) );  
        //根据该根节点生成JTree
    }
    
    private Node getXMLRoot( String text ){
        ByteArrayInputStream	byteStream;
        byteStream = new ByteArrayInputStream( text.getBytes() ); 
        //将XML文件读到Stream里去
        try{
          doc = db.parse( byteStream );  
          //解析该xml文件。
        } catch ( Exception e )
        { e.printStackTrace();}
        return ( Node )doc.getDocumentElement();
           //返回该XML文件的DOM树的根元素
}
    
    public DefaultMutableTreeNode createTreeNode( Node root ){
        DefaultMutableTreeNode  treeNode = null; 
           //定义要返回的根节点
        String name = root.getNodeName();
           //获得该节点的NodeName
           String value = root.getNodeValue(); 
           //获得该节点的NodeValue
       treeNode = new DefaultMutableTreeNode( root.
  getNodeType() == Node.TEXT_NODE ? value : name );
        //如果为值节点，那么取得该节点的值，否则取得该节点的Tag的名字 
        if ( root.hasChildNodes() ) 
        //如果该节点有孩子节点，那么递归处理该节点的孩子节点
        {  NodeList children = root.getChildNodes();  
          //取得该节点的子节点列表
           if( children != null ){       
           //判断子节点是否为空
            int numChildren = children.getLength();  
             //取得字节数目
              for (int i=0; i < numChildren; i++){  
                 Node node = children.item(i); 
                    //循环处理每个子节点
                 if( node != null )
                 {  if( node.getNodeType() == Node.ELEMENT_NODE )
                    { treeNode.add( createTreeNode(node) ); 
                    //如果该子节点还有孩子节点使用递归的方法处理该子节点
                    } else {
                   String data = node.getNodeValue();
                    if( data != null )
                    {
                       data = data.trim();
                       if ( !data.equals("\n") && !data.equals("\r\n") && 
  data.length() > 0 )
                       {    treeNode.add(new 
  DefaultMutableTreeNode(node.getNodeValue()));
                         //如果该节点没有孩子节点，那么直接加到节点下
                         }   
                     }  
                   } 
                 } 
              }
           }
        } 
        return treeNode;  //返回节点 
        }
    //构造函数，做初始化工作
    
    //从某个XML文件生成该树
    public void SaveToFile(DefaultMutableTreeNode root,FileWriter fw) {
    	try {
    	      if (root.isLeaf()) fw.write(root.toString()+"\r\n"); 
    	//如果是叶子节点则直接将该节点输出到文件中
    	else { //不是叶子节点的话递归输出该节点
    	      fw.write("<"+root.toString()+">\r\n"); 
    	     for (int i=0; i < root.getChildCount(); i++)
    	       { DefaultMutableTreeNode childNode =(DefaultMutableTreeNode) 
    	root.getChildAt(i);
    	         SaveToFile(childNode, fw); 
    	         //递归输出该节点的所有子节点 }
    	   fw.write("</"+root.toString()+">\r\n");
    	    }
    	}} catch (Exception e)
    	      {  e.printStackTrace();
    	      }
    }
    
}
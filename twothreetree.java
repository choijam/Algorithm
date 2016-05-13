import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.util.ArrayList;

class Tree23<K extends Comparable<K>, V>{
   
   public Node<K,V> root=null; 
   private int totalNodeNum=0;
   
   @SuppressWarnings("hiding")
   class  Node<K extends Comparable<K>, V>{
      K keyLeft, keyRight;
      V valueLeft,valueRight;
      Node<K,V> left_child,middle_child, right_child,parent;
      
      public Node()
      {
         this.keyLeft=null;
         this.keyRight=null;
          this.left_child=null;
          this.middle_child=null;
          this.right_child=null;
      }
      public Node(K key,V value)
      {
         this.keyLeft=key;
         this.valueLeft=value;
         this.keyRight=null;
         this.left_child=null;
         this.middle_child=null;
         this.right_child=null;
      }
      
      public Node(K key1, K key2){
         this.keyLeft=key1;
         this.keyRight=key2;
         this.left_child=null;
         this.middle_child=null;
         this.right_child=null;
      }
   }
   
   public void put(K key, V value){
      
    totalNodeNum++;  
    Node<K,V> where=search(key);
    
    Node<K,V> newnode;
   
     if((int)value>1){
        if(where.keyLeft.equals(key))
           where.valueLeft=value;
        else where.valueRight=value;
        totalNodeNum--;
        return ;
     }
     
      if(root==null&&where==null){
        newnode=new Node<K,V>(key,value);
         root=newnode;
         return ;
      }
      

      if(root!=null&&where.keyRight==null){
      
         if(where.keyLeft.compareTo(key)>0)
         {
            where.keyRight=where.keyLeft; where.valueRight=where.valueLeft;
            where.keyLeft=key; where.valueLeft=value;
         }
         
         else {
            where.keyRight=key;
            where.valueRight=value;
         }
         return ;
      }
      
      
      else if(root!=null&&where.keyRight!=null){
    	  
         newnode=new Node<K,V>(key,value);
         split(where.parent,where,newnode);
      }
       
       return ;
   }
   

public void split(Node<K,V> parent,Node<K,V> p,Node<K,V> newnode){
      
      Node<K,V> upNode=null;
    
      if(parent==null)
      {   
    	  
         upNode=new Node<K,V>();
      
			if (newnode.keyLeft.compareTo(p.keyLeft) < 0) {

				upNode.keyLeft = p.keyLeft;
				upNode.valueLeft = p.valueLeft;
				p.keyLeft = p.keyRight;
				p.valueLeft = p.valueRight;
				upNode.left_child = newnode;
				upNode.middle_child = p;

			}

			else if (p.keyRight.compareTo(newnode.keyLeft) < 0) {
				upNode.keyLeft = p.keyRight;
				upNode.valueLeft = p.valueRight;
				upNode.left_child = p;
				upNode.middle_child = newnode;
			}

			else {
				upNode.keyLeft = newnode.keyLeft;
				upNode.valueLeft = newnode.valueLeft;
				newnode.keyLeft = p.keyLeft;
				newnode.valueLeft = p.valueLeft;
				p.keyLeft = p.keyRight;
				p.valueLeft = p.valueRight;
				upNode.left_child = newnode;
				upNode.middle_child = p;
			}

			p.keyRight = null;
			p.valueRight = null;
			root = upNode;
			p.parent = upNode;
			newnode.parent = upNode;
			return;
		}
      
      
      else if(parent.keyRight==null){
        

          if(p==parent.left_child){
             parent.keyRight=parent.keyLeft; parent.valueRight=parent.valueLeft;
             if(p.keyLeft.compareTo(newnode.keyLeft)>0){
            	
                parent.keyLeft=p.keyLeft; parent.valueLeft=p.valueLeft;
                p.keyLeft=p.keyRight; p.valueLeft=p.valueRight;
                parent.right_child=parent.middle_child; parent.middle_child=p; parent.left_child=newnode;
             }
             
            
             else if(p.keyRight.compareTo(newnode.keyLeft)<0){
	               parent.keyLeft=p.keyRight; parent.valueLeft=p.valueRight;
	               parent.right_child=parent.middle_child; parent.middle_child=newnode;
              }
             
            
             else{ 	
                  parent.keyLeft=newnode.keyLeft; parent.valueLeft=newnode.valueLeft;
                  newnode.keyLeft=p.keyLeft; newnode.valueLeft=p.valueLeft;
                  p.keyLeft=p.keyRight; p.valueLeft=p.valueRight;
                   parent.right_child=parent.middle_child; parent.middle_child=p; parent.left_child=newnode;
             }
             
             p.keyRight=null; p.valueRight=null; 
             newnode.parent=parent;
          }
          
          
     
			else if (p == parent.middle_child) {
			
				if (p.keyLeft.compareTo(newnode.keyLeft) > 0) {
					parent.keyRight=p.keyLeft;
					parent.valueRight=p.valueLeft;
					
					parent.right_child=p;
					parent.middle_child=newnode;
					
					p.keyLeft=p.keyRight;
					p.valueLeft=p.valueRight;
					
					newnode.parent=parent;

				}

			
				else if (p.keyLeft.compareTo(newnode.keyLeft) < 0 && p.keyRight.compareTo(newnode.keyLeft) > 0) {
					parent.keyRight = newnode.keyLeft;
					parent.valueRight = newnode.valueLeft;
					newnode.keyLeft = p.keyLeft;
					newnode.valueLeft = p.valueLeft;
					p.keyLeft = p.keyRight;
					p.valueLeft = p.valueRight;
					parent.right_child = p;
					parent.middle_child = newnode;
				}

				
				else {
					parent.keyRight = p.keyRight;
					parent.valueRight = p.valueRight;
					parent.right_child = newnode;
				}
				p.keyRight = null;
				p.valueRight = null;
				newnode.parent = parent;
			}
		}
    
    
      else{
    	  
			upNode = new Node<K, V>();

			if (newnode.keyLeft.compareTo(p.keyLeft) < 0) {
				upNode.keyLeft = p.keyLeft;
				upNode.valueLeft = p.valueLeft;
				
				p.keyLeft = p.keyRight;
				p.valueLeft = p.valueRight;

				if (parent.middle_child == p) {

					upNode.left_child = parent.left_child;
					upNode.middle_child = newnode;
					
					newnode.parent = upNode;
					
					parent.left_child.parent = upNode;
					parent.left_child = p;
					parent.middle_child = parent.right_child;
					
					p.keyRight = null;
					p.valueRight = null;
					
					parent.right_child = null;
					
					split(parent.parent, parent, upNode);
				} 
				else {
					upNode.left_child = newnode;
					upNode.middle_child = p;
					
					p.parent = upNode;
					newnode.parent = upNode;
					
					if (parent.left_child == p) {
						
						parent.left_child = parent.middle_child;
						parent.middle_child = parent.right_child;
					}
					p.keyRight = null;
					p.valueRight = null;
					
					parent.right_child = null;
					
					split(parent.parent, parent, upNode);
				}

				return;
			}

			else if (newnode.keyLeft.compareTo(p.keyRight) > 0) {
				upNode.keyLeft = p.keyRight;
				upNode.valueLeft = p.valueRight;

				if (parent.middle_child == p) {
					upNode.left_child = parent.left_child;
					upNode.middle_child = p;
					
					parent.left_child.parent = upNode;
					parent.left_child = newnode;
					
					parent.middle_child = parent.right_child;
					newnode.parent = parent;
					
				}

				else {
					upNode.left_child = p;
					upNode.middle_child = newnode;
					newnode.parent = upNode;
					if (parent.left_child == p) {
						parent.left_child = parent.middle_child;
						parent.middle_child = parent.right_child;
					}
				}

				p.parent = upNode;
				p.keyRight = null;
				p.valueRight = null;
				parent.right_child = null;
				split(parent.parent, parent, upNode);
				return;
    	  }
    	  
			else {
		
				upNode.keyLeft = newnode.keyLeft;
				upNode.valueLeft = newnode.valueLeft;
				
				if (parent.middle_child == p) {
					upNode.left_child=parent.left_child;
					newnode.keyLeft=p.keyLeft;
					upNode.middle_child=newnode;
					newnode.parent=upNode;
					upNode.left_child.parent=upNode;
					p.keyLeft=p.keyRight;
					p.valueRight=p.valueRight;
					parent.middle_child=parent.right_child;
					parent.left_child=p;
					

				} else {
					
					upNode.left_child=newnode;
					upNode.middle_child=p;
					
					newnode.keyLeft=p.keyLeft;
					newnode.valueLeft=p.valueLeft;
					
					p.keyLeft=p.keyRight;
					p.valueLeft=p.valueRight;
					
					newnode.parent=upNode;

					if (parent.left_child == p) {
						parent.left_child = parent.middle_child;
						parent.middle_child = parent.right_child;
					}
					p.parent = upNode;
				}
				
				p.keyRight = null;
				p.valueRight = null;
				parent.right_child = null;
				split(parent.parent, parent, upNode);
				return;
			}

		}
      
   }
   
 

   public Node<K,V> search(K newkey){
      Node<K,V> where=root;
      Node<K,V> before=where;
      
      while(where!=null){
         before=where;
         
         if(where.keyLeft.compareTo(newkey)>0)
            where=where.left_child;
         else if(where.keyLeft.equals(newkey)){
            return where;
         }
         else if(where.keyRight==null&&where.keyLeft.compareTo(newkey)<0||where.keyRight.compareTo(newkey)>0&&where.keyLeft.compareTo(newkey)<0)
         {
            where=where.middle_child;
         }
         else if(where.keyRight!=null&&where.keyRight.equals(newkey))
            return where;
         else where=where.right_child;   
      }
      
      return before;
  }
 
   
   public boolean contains(K key)
   {
      return get(key)!=null;
   }
   
   public V get(K key){
      
      Node<K,V> where = search(key);
      
      if (where != null)
      {
         if (where.keyLeft.equals(key))
            return where.valueLeft;
         else if(where.keyRight!=null&&where.keyRight.equals(key)){
            return where.valueRight;
           
         }
         else return null;
      }
      
      else
         return null;
      
   }
   
   public int depth(){
          int depth = 0;
          Node<K,V> tempRoot= root; 
          
          if(tempRoot==null)
             return 0;
       
          while (tempRoot != null)
          {
             tempRoot = tempRoot.left_child;
             depth++;
          }
          
          return depth;
   }

   public int size()
   {
      return totalNodeNum;
   }

   public Iterable<K> keys()
   {
      if(root==null)
      {
         return null;
      }
      
      ArrayList<K> nodeKeys = new ArrayList<K>();
      inorder(root, nodeKeys);
      return nodeKeys;
   }
   
	public void inorder(Node<K, V> first, ArrayList<K> keys) {
		if (first == null) {
			return;
		}

		inorder(first.left_child, keys);
		keys.add(first.keyLeft);
		inorder(first.middle_child, keys);
		if (first.keyRight != null) {
			keys.add(first.keyRight);
			inorder(first.right_child, keys);
		}

	}
}


public class Hw3 {

	   public static void main(String[] args) {
	      Tree23<String, Integer> st = new Tree23<>();
	      final JFileChooser fc = new JFileChooser();      // 파일 선택기를 사용
	      File file;
	      if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
	             file = fc.getSelectedFile();
	      else  {
	            JOptionPane.showMessageDialog(null, "파일을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
	            return;
	      }
	      
	      Scanner sc = null;      // file에 있는 단어들을 키로 해서 테이블에 차례대로 저장
	      try {
	         // 이후, 테이블에 저장된 모든 (키,값)의 쌍들을 출력
	         sc = new Scanner(file);
	         long start = System.currentTimeMillis();
	         while (sc.hasNext()) {
	            String word = sc.next();
	            if (!st.contains(word))   st.put(word, 1);
	            else   st.put(word, st.get(word) + 1);
	         }
	         long end = System.currentTimeMillis();

	         System.out.println("입력 완료: 소요 시간 = " + (end-start) + "ms");
	         System.out.println("등록된 단어 수 = " + st.size());
	         System.out.println("트리의 깊이 = " + st.depth());
	         
	         /*int depth = st.depth(), i = 1;
	         
	         while (depth == st.depth()) {
	            String maxKey = "";
	            int maxValue = 0;
	            for (String word : st.keys())
	               if (st.get(word) > maxValue) {
	                  maxValue = st.get(word);
	                  maxKey = word;
	               }
	            
	            System.out.println(i + "번째 실행: " + maxKey + " " + maxValue);
	            st.delete(maxKey);
	            i++;
	         }*/

	         /*   주석을 해제하지 말 것. 
	         for (String word : st.keys()) {
	            System.out.println(word + "\t" + st.get(word));
	         }
	         */
	         
	      } catch (FileNotFoundException e) { e.printStackTrace(); }
	      if (sc != null)
	         sc.close();
	   }

	}

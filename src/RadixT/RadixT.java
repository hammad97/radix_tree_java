/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RadixT;
import java.util.Queue;
import java.util.LinkedList;
/**
 *
 * @author Dell
 */
public class RadixT {
    public RNode root;
    int subString;
    String subStringSearch;  //both variables for substring search
    
    class RNode
    {
        String data;                                                            //
        int tempUpdate;
        int myIndex;     //previous index
        int flag;  // 1 if complete 0 if not complete
        RNode link[];
        RNode()
        {
            tempUpdate=0;
            link=new RNode[26];
            flag=0;
            for(int i=0;i<26;i++)
                link[i]=null;
        }
        RNode(String a)
        {
            tempUpdate=0;
            flag=1;
            link=new RNode[26];
            for(int i=0;i<26;i++)
                link[i]=null;
            
           data=a;
        }
        
    }
   public RadixT()
   {
       subString=0;
       root=new RNode();
       root.myIndex=-1;
   }
   public void insert(String a)
   {
       System.out.println("Inserting "+a);
       a=a.toUpperCase();
       char fChar=a.charAt(0);
       StringBuilder s1=new StringBuilder(a);
       s1.deleteCharAt(0);
       a=s1.toString();
       int index=getHash(fChar);
       if(root.link[index]==null)
       {
            root.link[index]=new RNode(a);
            root.link[index].myIndex=index;
            root.link[index].flag=1;
       }
       else
       {
            insert(a,root.link[index]);
       }
       
   }
   
   private void insert(String a,RNode n)
   {
       if(n!=null)
       {
           int count=0;
           int len;
           if(n.data.length()<a.length())
               len=n.data.length();
           else
               len=a.length();
           for(int k=0;k<len;k++)
           {
               if(a.charAt(k)==n.data.charAt(k))
                   count++;
               else
                   k=len;   //better than break;
           }   
           if(count==n.data.length() && count==a.length() && n.flag==1)
           {
               System.out.println(revHash(n.myIndex)+a+" already present in tree");
           }
           else if(n.flag==0 && n.data.equalsIgnoreCase(a))
           {
               n.flag=1;
           }
           else if(count==n.data.length() && areLinksNull(n)==false)
           {
               StringBuilder s1=new StringBuilder(a);
               for(int i=1;i<=count;i++)
                   s1.deleteCharAt(0);
               a=s1.toString();
               int index=getHash(a.charAt(0));
               if(n.link[index]!=null)
                   insert(a,n.link[index]);
               else
                   n.link[index]=insert(a,n.link[index],1);
               
           }
           else if(areLinksNull(n)==true)
           {
               String temp=n.data;  // original data in n.data
               StringBuilder s1=new StringBuilder(temp);
               StringBuilder s2=new StringBuilder(temp);
               StringBuilder s3=new StringBuilder(a);
               for(int j=count;j<n.data.length();j++)
                   s2.deleteCharAt(count);    
               n.data=s2.toString(); //matched data

               for(int j=0;j<count;j++)
                   s1.deleteCharAt(0);
               temp=s1.toString();  //unmatched data here NODE
               
               
               for(int j=0;j<count;j++)
                   s3.deleteCharAt(0);
               a=s3.toString();  //unmatched data here PARAMETER
               char fChar;
               int index;

               if(a.isEmpty() || temp.isEmpty())    //ROM , ROME inputs
                   n.flag=1;
               else
                   n.flag=0;               

               
               if(!temp.isEmpty())
               {
                   fChar=temp.charAt(0);
                   index=getHash(fChar);               
                   n.link[index]=insert(temp,n.link[index],1);
                   n.link[index].myIndex=index;
               }
               
               if(!a.isEmpty())
               {
                   fChar=a.charAt(0);
                   index=getHash(fChar);
                   n.link[index]=insert(a,n.link[index],1);
                   n.link[index].myIndex=index;
               }
           }
       }
   }
   
   private RNode insert(String a,RNode n, int v)   //int v just added to do overriding 
   {
           n=new RNode(a);
           return n;
   }
   public boolean areLinksNull(RNode n)   //tells if links under a node are all null or not
   {
        int c=0;
        for(int i=0;i<26;i++)
        {
            if(n.link[i]!=null)
            {
                c++;
            }
        }
        if(c==0)
            return true;
        else
            return false;
   }   
           
   private int getHash(char ch)
   {
       return ch-65;
   }
   
   
   private char revHash(int a)
   {
       a=a+65;
       char b=(char)a;
       return b;
   }
   
   public void searchSubString(String a)
   {
       if(a.isEmpty() || root==null)
           return;
 
       System.out.println("(Sub)Searching "+a);       
       
       subString=1;  //telling display that substring is calling it now
       subStringSearch=a;
       display();
       subString=0;
       
       
   }
   public void display()
   {
        Queue<RNode> q = new LinkedList<RNode>();
        if (root == null)
        return;

        System.out.println("Current Tree: ");

        String temp;
        for(int i=0;i<26;i++)
        {
            if(root.link[i]!=null)
                q.add(root.link[i]);            
        }     

        while (!q.isEmpty()) 
        {
            RNode n = (RNode) q.remove();
            if(n.flag==0 || (n.flag==1 && this.areLinksNull(n)==false))
            {
                if(n.flag==1)
                {
                    if(this.subString==1)
                    {
                        StringBuilder s1=new StringBuilder(Character.toString(revHash(n.myIndex)));
                        s1.append(n.data);
                        temp=s1.toString();
                        if(temp.startsWith(subStringSearch))
                            System.out.println(temp);                            
                    }
                    else
                        System.out.println(revHash(n.myIndex)+n.data);
                }                
                char ch=revHash(n.myIndex);
                temp=Character.toString(ch);
                            
                int tempUpdate=0;
                
                StringBuilder s1=new StringBuilder(temp);
                s1.append(n.data);
                temp=s1.toString();
                String temp2=temp;
                
                Queue<RNode> q2 = new LinkedList<RNode>();
                for(int i=0;i<26;i++)
                {
                    if(n.link[i]!=null)
                        q2.add(n.link[i]);            
                }
                
                while (!q2.isEmpty()) 
                {         
                    RNode n2 = (RNode) q2.remove();
                    if(areLinksNull(n2)==false)
                    {
                        s1=new StringBuilder(temp2);
                        s1.append(n2.data);
                        temp2=s1.toString();
                        for(int i=0;i<26;i++)
                        {
                            if(n2.link[i]!=null)
                            {
                                q2.add(n2.link[i]);
                                n2.link[i].tempUpdate=1;
                            }            
                        }                                                
                    }
                    if(n2.tempUpdate!=1)
                    {
                        if(this.subString==1)
                        {
                            StringBuilder ss=new StringBuilder(temp);                    
                            ss.append(n2.data);
//                            temp=ss.toString();
                            if(ss.toString().startsWith(subStringSearch))
                                System.out.print(ss.toString());                            
                        }
                        else
                            System.out.print(temp+n2.data);
                    }
                    else
                    {
                        if(this.subString==1)
                        {
                            StringBuilder ss=new StringBuilder(temp2);
                            ss.append(n2.data);
                            temp=ss.toString();
                            if(temp.startsWith(subStringSearch))
                                System.out.print(temp);                            
                        }
                        else
                            System.out.print(temp2+n2.data);
                    }                        
                    System.out.println();
                }       
            }
            else if(n.flag==1)
            {
                if(this.subString==1)
                {
                    StringBuilder s1=new StringBuilder(Character.toString(revHash(n.myIndex)));
                    s1.append(n.data);
                    temp=s1.toString();             
                    if(temp.startsWith(subStringSearch))
                        System.out.println(temp);                            
                }
                else
                    System.out.println(revHash(n.myIndex)+n.data);
            }
        }
        System.out.println();
   }
   public void search(String target)
   {
       if(target.isEmpty())
           return;
        System.out.println("(Full)Searching "+target);       
        int found=0;
        Queue<RNode> q = new LinkedList<RNode>();
        if (root == null)
        return;
        
        for(int i=0;i<26;i++)
        {
            if(root.link[i]!=null)
                q.add(root.link[i]);            
        }     

        while (!q.isEmpty()) 
        {
            RNode n = (RNode) q.remove();
            if(n.flag==0 || (n.flag==1 && areLinksNull(n)==false))
            {
                if(n.flag==1)
                {
                    StringBuilder s1=new StringBuilder(Character.toString(revHash(n.myIndex)));
                    s1.append(n.data);

                    if(s1.toString().equalsIgnoreCase(target))
                    {    System.out.println(target+" is present in tree");
                         found=1;
                    }

                }                
                char ch=revHash(n.myIndex);
                String temp=Character.toString(ch);
                
                StringBuilder s1=new StringBuilder(temp);
                s1.append(n.data);
                temp=s1.toString();
                String temp2=temp;
                
                Queue<RNode> q2 = new LinkedList<RNode>();
                for(int i=0;i<26;i++)
                {
                    if(n.link[i]!=null)
                        q2.add(n.link[i]);            
                }
                
                while (!q2.isEmpty()) 
                {         
                    RNode n2 = (RNode) q2.remove();
                    if(areLinksNull(n2)==false)
                    {
                        s1=new StringBuilder(temp2);
                        s1.append(n2.data);
                        temp2=s1.toString();
                        for(int i=0;i<26;i++)
                        {
                            if(n2.link[i]!=null)
                            {
                                q2.add(n2.link[i]);
                                n2.link[i].tempUpdate=1;
                            }            
                        }                                                
                    }
                    if(n2.tempUpdate!=1)
                    {
//                        System.out.print(temp+n2.data);
                        StringBuilder s2=new StringBuilder(temp);
                        s2.append(n2.data);
                        if(s2.toString().equalsIgnoreCase(target))
                        {    System.out.println(target+" is present in tree");
                             found=1;
                        }
                    }
                    else
                    {
                        StringBuilder s2=new StringBuilder(temp2);
                        s2.append(n2.data);
                        if(s2.toString().equalsIgnoreCase(target))
                        {    System.out.println(target+" is present in tree");
                             found=1;
                        }
                    }                 
                }       
            }
            else if(n.flag==1)
            {
                StringBuilder s1=new StringBuilder(Character.toString(revHash(n.myIndex)));
                s1.append(n.data);
                if(s1.toString().equalsIgnoreCase(target))
                {    System.out.println(target+" is present in tree");
                     found=1;
                }
            }
        }
        if(found!=1)
            System.out.println(target+" is NOT present in tree");            
   }   

   public void delete(String target)
   {
       if(target.isEmpty())
           return;

       System.out.println("Deleting "+target);
       
        int found=0;
        RNode pre=root;
        Queue<RNode> q = new LinkedList<RNode>();
        if (root == null)
        return;
        
        for(int i=0;i<26;i++)
        {
            if(root.link[i]!=null)
                q.add(root.link[i]);            
        }     

        while (!q.isEmpty()) 
        {
            RNode n = (RNode) q.remove();
            if(n.flag==0 || (n.flag==1 && this.areLinksNull(n)==false))
            {
                if(n.flag==1)
                {
                    StringBuilder s1=new StringBuilder(Character.toString(revHash(n.myIndex)));
                    s1.append(n.data);
                    if(s1.toString().equalsIgnoreCase(target))
                    {    
                         found=1;
                         if(areLinksNull(n))
                             pre.link[n.myIndex]=null;
                         else
                             n.flag=0;
                         break;
                    }

                }                
                char ch=revHash(n.myIndex);
                String temp=Character.toString(ch);
                int tempUpdate=0;
                
                StringBuilder s1=new StringBuilder(temp);
                s1.append(n.data);
                temp=s1.toString();
                String temp2=temp;
                
                Queue<RNode> q2 = new LinkedList<RNode>();
                for(int i=0;i<26;i++)
                {
                    if(n.link[i]!=null)
                        q2.add(n.link[i]);            
                }
                
                while (!q2.isEmpty()) 
                {         
                    RNode n2 = (RNode) q2.remove();
                    if(areLinksNull(n2)==false)
                    {
                        s1=new StringBuilder(temp2);
                        s1.append(n2.data);
                        temp2=s1.toString();
                        for(int i=0;i<26;i++)
                        {
                            if(n2.link[i]!=null)
                            {
                                q2.add(n2.link[i]);
                                n2.link[i].tempUpdate=1;
                            }            
                        }                                                
                    }
                    if(n2.tempUpdate!=1)
                    {
                        StringBuilder s2=new StringBuilder(temp);
                        s2.append(n2.data);
                        if(s2.toString().equalsIgnoreCase(target))
                        {
                             if(areLinksNull(n2))
                                 n.link[n2.myIndex]=null;
                             else
                                 n2.flag=0;
                             found=1;
                             break;
                        }
                    }
                    else
                    {
                        StringBuilder s2=new StringBuilder(temp2);
                        s2.append(n2.data);
                        if(s2.toString().equalsIgnoreCase(target))
                        {    
                             if(areLinksNull(n2))
                                 n.link[n2.myIndex]=null;
                             else
                                 n2.flag=0;
                            found=1;
                            break;
                        }
                    }                      
                }       
            }
            else if(n.flag==1)
            {
                StringBuilder s1=new StringBuilder(Character.toString(revHash(n.myIndex)));
                s1.append(n.data);
                if(s1.toString().equalsIgnoreCase(target))
                {    
                    if(areLinksNull(n))
                        pre.link[n.myIndex]=null;
                    else
                        n.flag=0;
                    found=1;
                    break;
                }
            }
            pre=n;
        }
        if(found!=1)
            System.out.println(target+" is NOT present in tree");
   }      
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        RadixT r1=new RadixT();
//        r1.insert("ROME");
//        r1.insert("ANT");
//        r1.insert("BOY");
//        r1.insert("ZED");
//        r1.insert("IRON");                
//        r1.insert("ROMAN");
//        r1.insert("ROM");
//        r1.insert("ROMANE");
//        r1.insert("IRONMAN");
//        r1.insert("ANTMAN");
//        r1.insert("ANTWOMAN");
//        r1.display(); 
//
//        r1.delete("ANT");
//        r1.display(); 
//
//        r1.search("ANT");
//        r1.display(); 
//        
//        r1.delete("ROM");
//        r1.display(); 
//
//        r1.search("ROM");
//        r1.display();         
//        
//        r1.searchSubString("RO");
//        r1.search("WOMAN");
//    }    
}

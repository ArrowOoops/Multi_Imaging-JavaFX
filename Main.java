package application;
	
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.sun.javafx.iio.ImageStorage.ImageType;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	public static int Resol_target_width=1600;										//目标图片水平像素点个数
	public static int Resol_target_height=2400;										//目标图片竖直像素点个数
	public static int src_pic_Num=150;										        //图片库源图片个数
	public static int section_Num=25600;											//所分段数
	public static int line_Num=160;												//每行列的划分数
	public static String target_pic_Path="Pic_Src\\86.jpg";                                                                 //合成目标文件名称
	public static boolean temp=true;
	public static int Resol_src_width=Resol_target_width/line_Num;								//源图片水平像素点个数
	public static int Resol_src_height=Resol_target_height/line_Num;							//源图片竖直像素点个数
	public static int[] ReadRGB(String FilePath) throws IOException
	{
		BufferedImage image=null;
		File f=null;
		f=new File(FilePath);
		image=ImageIO.read(f);
		int width=image.getWidth();
		int height=image.getHeight();
		int i,j;
		int size=width*height;
		int[]R=new int[size];
		int[]G=new int[size];
		int[]B=new int[size];
		int cnt=0;
		
		for(i=0;i!=height;++i)
			for(j=0;j!=width;++j)
			{
				int p=image.getRGB(j, i);
				R[cnt]=(p>>16)&0xff;
				G[cnt]=(p>>8)&0xff;
				B[cnt]=p&0xff;
				cnt++;
			}
		long totalR=0;
		long totalG=0;
		long totalB=0;
		for(i=0;i!=size;++i)
		{
			totalR=totalR+R[i];
		}
		for(i=0;i!=size;++i)
		{
			totalG=totalG+G[i];
		}
		for(i=0;i!=size;++i)
		{
			totalB=totalB+B[i];
		}
		int avgR=(int) (totalR/size);
		int avgG=(int) (totalG/size);
		int avgB=(int) (totalB/size);
		//System.out.println("Picture Data of "+FilePath+" : R="+avgR+" G="+avgG+" B="+avgB);
		int[]data=new int[3];
		data[0]=avgR;
		data[1]=avgG;
		data[2]=avgB;
		return data;		
	}
	public static int[][] DivideRGB(String FilePath) throws IOException
	{
		BufferedImage image=null;
		File f=null;
		f=new File(FilePath);
		image=ImageIO.read(f);
		//int width=image.getWidth();
		//int height=image.getHeight();
		int xlength=line_Num;
		int ylength=line_Num;
		int xcord=0;
		int ycord=0;
		int i=0;
		int j=0;
		int p=0;
		int q=0;
		int size=Resol_src_height*Resol_src_width;
		int[]R=new int[size];
		int[]G=new int[size];
		int[]B=new int[size];
		int[][]ResRGB=new int[section_Num][3];
		int cntr=0;
		for(i=0;i!=xlength;++i)
			for(j=0;j!=ylength;++j)
			{
				xcord=i*Resol_src_width;
				ycord=j*Resol_src_height;
				int cnt=0;
				for(p=xcord;p!=xcord+Resol_src_width;++p)
					for(q=ycord;q!=ycord+Resol_src_height;++q)
					{
						int data=image.getRGB(p, q);
						R[cnt]=(data>>16)&0xff;
						G[cnt]=(data>>8)&0xff;
						B[cnt]=data&0xff;
						cnt++;
					}
				long totalR=0;
				long totalG=0;
				long totalB=0;
				for(p=0;p!=size;++p)
				{
					totalR=totalR+R[p];
				}
				for(p=0;p!=size;++p)
				{
					totalG=totalG+G[p];
				}
				for(p=0;p!=size;++p)
				{
					totalB=totalB+B[p];
				}
				int avgR=(int) (totalR/size);
				int avgG=(int) (totalG/size);
				int avgB=(int) (totalB/size);
				ResRGB[cntr][0]=avgR;
				ResRGB[cntr][1]=avgG;
				ResRGB[cntr][2]=avgB;
				cntr++;
			}
		return ResRGB;
	}
	public static int ChoosePicture(int[][]input,int[]target)
	{
		int i=0;
		int r=target[0];
		int g=target[1];
		int b=target[2];
		int[]Diss=new int[input.length];
		for(i=0;i!=input.length;++i)
		{
			int rtemp=input[i][0];
			int gtemp=input[i][1];
			int btemp=input[i][2];
			Diss[i]=(r-rtemp)*(r-rtemp)+(g-gtemp)*(g-gtemp)+(b-btemp)*(b-btemp);
		}
		int min=0xffff;
		int flag=0;
		for(i=0;i!=input.length;++i)
		{
			if(Diss[i]<min)
			{
				min=Diss[i];
				flag=i;
			}
		}
		return flag;
	}
	public static void Generate(int[]Chosen,String[] FilePath) throws IOException
	{
		int i=0;
		int j=0;
		BufferedImage[] image=new BufferedImage[section_Num];
		File[]f =new File[section_Num];
		for(i=0;i!=section_Num;++i)
		{
			f[i]=new File(FilePath[Chosen[i]]);
			image[i]=ImageIO.read(f[i]);
		}
		File ftemp=new File("Pic_Src\\Template.jpg");
		BufferedImage Res=ImageIO.read(ftemp);
		int xlength=line_Num;
		int ylength=line_Num;
		int cnt_image=0;
		int xcord=0;
		int ycord=0;
		int p=0;
		int q=0;
		for(i=0;i!=xlength;++i)
			for(j=0;j!=ylength;++j)
			{
				xcord=i*Resol_src_width;
				ycord=j*Resol_src_height;
				for(p=xcord;p!=xcord+Resol_src_width;++p)
					for(q=ycord;q!=ycord+Resol_src_height;++q)
					{
						int data=image[cnt_image].getRGB(p-xcord, q-ycord);					
						Res.setRGB(p, q, data);
					}
				cnt_image++;
			}
		File fout = new File("Result.jpg");
        ImageIO.write(Res, "jpg", fout);
	}
	
	public static void Zoom(int width,int height,String FilePath,int No) throws Exception
	{
		double sx=0.0;
		double sy=0.0;
		File file=new File(FilePath);
		if(!file.isFile())
		{
			throw new Exception("Not a image!");
		}
		BufferedImage image=ImageIO.read(file);
		sx=(double)width/image.getWidth();
		sy=(double)height/image.getHeight();
		
		AffineTransformOp op=new AffineTransformOp(AffineTransform.getScaleInstance(sx, sy), null);
		File Res=new File("Zoom\\Z"+No+".jpg");
		BufferedImage zoomImage=op.filter(image, null);
		ImageIO.write((BufferedImage)zoomImage,"jpg", Res);
	}
	public static void setPath(String FilePath)
	{
		target_pic_Path=FilePath;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("FXScene.fxml"));//修改了
            //BorderPane root = new BorderPane();
            //设置Scene的大小(SceneBuilder中点击AnchorPane右边Layout中会显示大小,不一样的可以)
            Scene scene = new Scene(root);//修改了
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            //primaryStage.setResizable(false);//设置不能窗口改变大小
            
    		
//    		BorderPane root1 = new BorderPane();
//    		File f=new File("Result.jpg");
//			String local=f.toURI().toURL().toString();
//			
//			Image localimage = new Image(local,false);
//			ImageView imageView = new ImageView();
//			imageView.setImage(localimage);
//			root1.setCenter(imageView);
            
            
            primaryStage.setTitle("千图成像-PB16001763揭一新");//设置标题
            primaryStage.getIcons().add(new Image("icon.jpg"));

            primaryStage.show();
                      

		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main() throws Exception {
		//launch(args);
		int i,j,k=0;
		int req_width=Resol_src_width*line_Num;
		int req_height=Resol_src_height*line_Num;
		BufferedImage target_image=null;
		File f=null;
		f=new File(target_pic_Path);
		target_image=ImageIO.read(f);
		if(req_width>target_image.getWidth()||req_height>target_image.getHeight())
		{
			Zoom(req_width, req_height, target_pic_Path, 214748);
			target_pic_Path="Zoom\\Z214748.jpg";
		}
		String[] FilePath=new String[src_pic_Num];							//源图片读取路径
		String[] FilePathZ=new String[src_pic_Num];							//压缩后读取路径
		for(i=0;i!=src_pic_Num;++i)
		{
			FilePath[i]="Pic_Src\\"+(i+1)+".jpg";
			//System.out.println(FilePath[i]);
		}
		for(i=0;i!=src_pic_Num;++i)
		{
			FilePathZ[i]="Zoom\\Z"+(i+1)+".jpg";
			//System.out.println(FilePath[i]);
		}
		int[][]SgsRGBRes=new int[src_pic_Num][3];							//源图片的平均RGB
		for(i=0;i!=src_pic_Num;++i)
		{
			SgsRGBRes[i]=ReadRGB(FilePath[i]);
			//System.out.println("Information of picture "+FilePath[i]+" is: r= "+SgsRGBRes[i][0]+" g= "+SgsRGBRes[i][1]+" b= "+SgsRGBRes[i][2]);
		}
		int[][] InforTarget=new int[section_Num][3];
		InforTarget=DivideRGB(target_pic_Path);
//		for(i=0;i!=100;++i)
//		{
//			System.out.println("Section "+(i+1)+" : r= "+InforTarget[i][0]+" g= "+InforTarget[i][1]+" b= "+InforTarget[i][2]);
//		}
//		for(i=0;i!=80;++i)
//		{
//			System.out.println("r= "+SgsRGBRes[i][0]+" g= "+SgsRGBRes[i][1]+" b= "+SgsRGBRes[i][2]);
//		}
		int[]Chosen=new int[section_Num];									//每区域最接近的源图片信息
		for(i=0;i!=section_Num;++i)
		{
			Chosen[i]=ChoosePicture(SgsRGBRes, InforTarget[i]);
			//System.out.println("Best Chosen picture of sectionNo "+(i+1)+" is "+(Chosen[i]+1));
		}
		for(i=0;i!=src_pic_Num;++i)
		{
			Zoom(Resol_src_width,Resol_src_height,FilePath[i],i+1);
		}
		Generate(Chosen, FilePathZ);
		System.out.println("Finished!");
		
	}
}


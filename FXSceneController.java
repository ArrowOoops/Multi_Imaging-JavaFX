package application;

import java.awt.MenuItem;
import java.io.File;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

public class FXSceneController {
	@FXML
	private TextField text_1;
	@FXML
	private Button btn_1;
	@FXML
	private Button btn_2;
	@FXML
	private Button btn_3;
	@FXML
	private Button btn_4;
	@FXML
	private Label lab_1;
	@FXML
	private MenuItem num_1;
	@FXML
	private MenuItem num_2;
	@FXML
	private MenuButton menubtn;
	
	
	public static boolean click_btn2=false;
	public static String Path= "";
	public static int num=0;
	public static String display= "";

	public void OnClick_btn1() throws Exception
	{
		String text;
		if(click_btn2)
		{
			text="test\\"+Path;
			text_1.setEditable(true);
			text_1.setText(text);
			
		}
		else
		{
			text =text_1.getText();
			text="test\\"+text+".jpg";
			System.out.println(text);
		}
		lab_1.setText("输入完成！请等待处理……");
		Main.setPath(text);
		System.out.println(text);
		Main.main();
		lab_1.setText("处理成功！");
	}

	public void OnClick_btn2() throws Exception
	{
//		Parent target = FXMLLoader.load(getClass().getResource("Chose.fxml"));//载入窗口B的定义文件；<span style="white-space:pre">	</span>
//		Scene scene = new Scene(target); //创建场景；
//		Stage stg=new Stage();//创建舞台；
//		stg.setScene(scene); //将场景载入舞台；
//		stg.show(); //显示窗口；
		click_btn2=true;
		Stage primaryStage=new Stage();
		//BorderPane root = new BorderPane();
		//Scene scene = new Scene(root,400,400);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		//primaryStage.setScene(scene);
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        //Path=(String)file;
        System.out.println(file);
        Path=file.getName();
        System.out.println(Path);
        //primaryStage.show();
		
		
	}
	public void OnClick_btn3() throws Exception
	{
		Parent target = FXMLLoader.load(getClass().getResource("Display.fxml"));//载入窗口B的定义文件；<span style="white-space:pre">	</span>
		Scene scene = new Scene(target); //创建场景；
		Stage stg=new Stage();//创建舞台；
		stg.setScene(scene); //将场景载入舞台；
		stg.show(); //显示窗口；
	}
	
	public void OnClick_btn4() throws Exception
	{
		//FileChooser fileChooser = new FileChooser();
		//Runtime.getRuntime().exec("cmd /c start C:\\Users\\ArrowOoops\\eclipse-workspace\\千图成像_FX\\Result.jpg");
		Runtime.getRuntime().exec("cmd /c start Result.jpg");
		Process process = Runtime.getRuntime().exec("Result.jpg");
	}
	public void ChoseNum1()
	{
		num=1600;
		Main.section_Num=1600;
		Main.line_Num=40;
		Main.Resol_src_width=Main.Resol_target_width/Main.line_Num;
		Main.Resol_src_height=Main.Resol_target_height/Main.line_Num;
		display=display+num;
		menubtn.setText(display);
		display="";
	}
	public void ChoseNum2()
	{
		num=6400;
		Main.section_Num=6400;
		Main.line_Num=80;
		Main.Resol_src_width=Main.Resol_target_width/Main.line_Num;
		Main.Resol_src_height=Main.Resol_target_height/Main.line_Num;
		display=display+num;
		menubtn.setText(display);
		display="";
	}
	public void ChoseNum3()
	{
		num=25600;
		Main.section_Num=25600;
		Main.line_Num=160;
		Main.Resol_src_width=Main.Resol_target_width/Main.line_Num;
		Main.Resol_src_height=Main.Resol_target_height/Main.line_Num;
		display=display+num;
		menubtn.setText(display);
		display="";
	}
}

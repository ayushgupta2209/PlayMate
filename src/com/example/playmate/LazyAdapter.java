package com.example.playmate;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.playmate.models.User_Post;

public class LazyAdapter extends BaseExpandableListAdapter {
    
    private Activity activity;
    private ArrayList<User_Post> data;
    private ArrayList<User_Post> _listDataChild;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    private Context _context;
 //   private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
   // private HashMap<String, List<String>> _listDataChild;
 
    public LazyAdapter(Context context, ArrayList<User_Post> d,
            ArrayList<User_Post> listChildData) {
        this._context = context;
        this.data = d;
        this._listDataChild = listChildData;
        
        imageLoader=new ImageLoader(context);
    }
    
  /*  
    public LazyAdapter(Activity a, ArrayList<User_Post> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }   */


    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getGroupView(final int position,final boolean isExpanded, View convertView, final ViewGroup parent) {
        View vi=convertView;
    //    String headerTitle=(String)getGroup(position);
        if(convertView==null)
        {
        	 LayoutInflater infalInflater = (LayoutInflater) this._context
                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView = infalInflater.inflate(R.layout.list_row, null);
        	
        }
        
        final Context context = parent.getContext();      
        
        TextView user_name = (TextView)convertView.findViewById(R.id.user_name); // User Name
        
     //   user_name.setText(headerTitle);
        TextView post = (TextView)convertView.findViewById(R.id.post); // Post of the User
        TextView distance = (TextView)convertView.findViewById(R.id.dist); // Distance
        ImageView thumb_image=(ImageView)convertView.findViewById(R.id.list_image); // Thumb image
        final ImageButton smiley=(ImageButton)convertView.findViewById(R.id.smiley); 
        ImageButton comment=(ImageButton)convertView.findViewById(R.id.comment);
        final ImageButton enter_comment=(ImageButton)convertView.findViewById(R.id.enter_comment);
        final EditText edit_comment=(EditText)convertView.findViewById(R.id.editText_comment);
        
        int comment_id=isExpanded?android.R.drawable.arrow_up_float:android.R.drawable.arrow_down_float;
    
        smiley.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {

				smiley.setImageResource(R.drawable.smiley_green);
			
			}});
        
        comment.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {

				 if(isExpanded){ ((ExpandableListView) parent).collapseGroup(position);
			//	 edit_comment.setVisibility(v.INVISIBLE);
			//	 enter_comment.setVisibility(v.INVISIBLE);
				 }
		          else 
		          {
		        //	  edit_comment.setVisibility(v.VISIBLE);
		        //	  enter_comment.setVisibility(v.VISIBLE);
		        	  ((ExpandableListView) parent).expandGroup(position, true);  
		          }
			}});
        
        User_Post user = new User_Post();
        user = data.get(position);
        
       //  Setting all values in listview
        user_name.setText(user.getUserName());
        post.setText(user.getPost());
        
        Double dist=Double.parseDouble(user.getDistance());
        distance.setText(String.valueOf(dist*1.60934));
        imageLoader.DisplayImage(user.getImage(), thumb_image);
        return convertView;
    }

    
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		 return this.data.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		 return this._listDataChild
				 //.get(this.data.get(groupPosition))
	                .size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		 return this.data.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return this._listDataChild
				//.get(this.data.get(groupPosition))
                .get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}


	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}


	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	//	final String childText = (String) getChild(groupPosition, childPosition);
		Context child_context=parent.getContext();
		
		
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.comment_list_item, null);
        }
        LinearLayout containerLayout = (LinearLayout)convertView.findViewById(R.id.ll);
        
        TextView txtListChild = (TextView)convertView.findViewById(R.id.name);
                
        if (childPosition == getChildrenCount(groupPosition) - 1) {
            // This gives you confirmation that is the last child in the group
        	EditText editText = new EditText(child_context);
            containerLayout.addView(editText);
            editText.hasFocus();
            editText.setHint("Enter your comment here");
            LayoutParams lp1=(LayoutParams) txtListChild.getLayoutParams();
            lp1.weight=1;
            editText.setLayoutParams(lp1);           
            editText.setTextSize(10);
            
            Button button_send=new Button(child_context);
            containerLayout.addView(button_send);
            LayoutParams lp2=(LayoutParams) ((TextView)convertView.findViewById(R.id.comment)).getLayoutParams();
            button_send.setHeight(15);
            button_send.setWidth(15);
          //  button_send.setLayoutParams(lp2);
            button_send.setText("Send");
            
            button_send.setTextSize(10);
            
       }
        txtListChild.setText("test");
        return convertView;
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
}
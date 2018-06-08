package com.ezzat.bookstore.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Model.Book;
import com.ezzat.bookstore.Model.Cart;
import com.ezzat.bookstore.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookInfo extends AppCompatActivity {

    Toolbar toolbar;
    int priority;
    private ImageView logout, profile, promote, back, makeOrder, confirmOrder, statistics;
    private TextView title, isbn, publiser, year, cat, price, author, vv, min, num;
    private EditText quan;
    private Button order, editTitle, editpub, editauth, edityear, editcat, editprice,confirm, cancel, editmin, editnum;
    LinearLayout admin, admin2, admin3;
    private Book book;
    private BookInfo self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        self = this;
        book = (Book) getIntent().getSerializableExtra("book");
        Log.i("dodo", book.getISBN()+"");
        priority = getIntent().getIntExtra("pri",0);
        Log.i("dodo", priority+"");
        setup_toolbar();
        setup_views();
        setup_buttons();
    }

    private void setup_buttons() {
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNum(quan.getText().toString())) {
                    Intent go = new Intent(BookInfo.this, HomeActivity.class);
                    go.putExtra("pri", priority);
                    Cart cart = (Cart) getIntent().getSerializableExtra("cart");
                    cart.books.add(book);
                    cart.quan.add(quan.getText().toString());
                    go.putExtra("cart", cart);
                    startActivity(go);
                } else {
                    new AlertDialog.Builder(BookInfo.this)
                            .setTitle("Error Num")
                            .setMessage("You entered a wrong quantity")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Whatever...
                                }
                            }).show();
                }
            }
        });

        editcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(cat);
            }
        });

        editprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(price);
            }
        });

        editauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(author);
            }
        });

        editpub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(publiser);
            }
        });

        editTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(title);
            }
        });

        edityear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(year);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BookInfo.editBook().execute(new String[]{isbn.getText().toString(), title.getText().toString()
                        , publiser.getText().toString(), year.getText().toString(), price.getText().toString(),
                        cat.getText().toString(), num.getText().toString(), min.getText().toString()});
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, HomeActivity.class);
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });
    }

    void boilr(final TextView te) {
        LayoutInflater li = LayoutInflater.from(self);
        View promptsView = li.inflate(R.layout.edit_dialog_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                self);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText editText = (EditText) promptsView
                .findViewById(R.id.edit);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                te.setText(editText.getText().toString());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void setup_views() {
        title = findViewById(R.id.title);
        isbn = findViewById(R.id.isbn);
        publiser = findViewById(R.id.pub);
        year = findViewById(R.id.year);
        cat = findViewById(R.id.cat);
        price = findViewById(R.id.price);
        author = findViewById(R.id.auth);
        admin2 = findViewById(R.id.admin2);
        admin3 = findViewById(R.id.admin3);
        num = findViewById(R.id.num);
        min = findViewById(R.id.min);
        editmin = findViewById(R.id.editmin);
        editnum = findViewById(R.id.editnum);
        title.setText(book.getTitle());
        isbn.setText(book.getISBN()+"");
        publiser.setText(book.getPublisher());
        year.setText(book.getPub_year()+"");
        cat.setText(book.getCategory());
        price.setText(book.getPrice()+"");
        min.setText(book.getMin_quantity() + "");
        num.setText(book.getNo_copies() + "");
        editauth = findViewById(R.id.editauth);
        editcat = findViewById(R.id.editcat);
        editprice = findViewById(R.id.editprice);
        editpub = findViewById(R.id.editpub);
        editTitle= findViewById(R.id.editTitle);
        edityear = findViewById(R.id.edityear);
        vv = findViewById(R.id.vv);
        cancel = findViewById(R.id.cancel);
        confirm = findViewById(R.id.confirm);
        admin = findViewById(R.id.admin);
        String str = "";
        for (String x : book.getAuthor()) {
            str = str + x + " , ";
        }
        author.setText(str);
        quan = findViewById(R.id.count);
        order = findViewById(R.id.order);
        if (priority == 0) {
            edityear.setVisibility(View.GONE);
            editTitle.setVisibility(View.GONE);
            editpub.setVisibility(View.GONE);
            editprice.setVisibility(View.GONE);
            editauth.setVisibility(View.GONE);
            editcat.setVisibility(View.GONE);
            admin.setVisibility(View.GONE);
            admin2.setVisibility(View.GONE);
            admin3.setVisibility(View.GONE);
        } else {
            edityear.setVisibility(View.VISIBLE);
            editTitle.setVisibility(View.VISIBLE);
            editpub.setVisibility(View.VISIBLE);
            editprice.setVisibility(View.VISIBLE);
            editauth.setVisibility(View.VISIBLE);
            editcat.setVisibility(View.VISIBLE);
            order.setVisibility(View.GONE);
            quan.setVisibility(View.GONE);
            vv.setVisibility(View.GONE);
            admin.setVisibility(View.VISIBLE);
            admin2.setVisibility(View.VISIBLE);
            admin3.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkNum(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void setup_toolbar() {
        setSupportActionBar(toolbar);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        logout = toolbar.findViewById(R.id.logout);
        profile = toolbar.findViewById(R.id.profile);
        promote = toolbar.findViewById(R.id.promote);
        back = toolbar.findViewById(R.id.back);
        makeOrder = toolbar.findViewById(R.id.placeOrders);
        confirmOrder = toolbar.findViewById(R.id.confirmOrders);
        statistics = toolbar.findViewById(R.id.statistics);
        if (priority == 0) {
            promote.setVisibility(View.GONE);
            makeOrder.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            statistics.setVisibility(View.GONE);
        } else {
            promote.setVisibility(View.VISIBLE);
            makeOrder.setVisibility(View.VISIBLE);
            confirmOrder.setVisibility(View.VISIBLE);
            statistics.setVisibility(View.VISIBLE);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, WelcomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                startActivity(intent);
            }
        });

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, HomeActivity.class);
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public class editBook extends AsyncTask<String, Void, Void> {

        HttpJsonParser jParser = new HttpJsonParser();
        /**
         * getting All products from url
         * */
        protected Void doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            params.put("isbn", args[0]);
            params.put("title", args[1]);
            params.put("pub", args[2]);
            params.put("year", args[3]);
            params.put("price", args[4]);
            params.put("cat", args[5]);
            params.put("num", args[6]);
            params.put("min", args[7]);
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/editBook.php", "GET", params);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
                Intent intent = new Intent(BookInfo.this, HomeActivity.class);
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
        }
    }
}

//intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
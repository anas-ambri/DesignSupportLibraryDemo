package com.verybadalloc.designlib.adapters.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.verybadalloc.designlib.R;
import com.verybadalloc.designlib.events.BookSelected;
import com.verybadalloc.designlib.events.BusProvider;
import com.verybadalloc.designlib.model.Book;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by aambri on 15-06-14.
 */
@LayoutId(R.layout.books_list_item_view)
public class BookItemViewHolder extends ItemViewHolder<Book> {
    @ViewId(R.id.book_image)
    ImageView image;
    @ViewId(R.id.book_author)
    TextView author;
    @ViewId(R.id.book_name)
    TextView name;
    @ViewId(R.id.book_type)
    TextView type;


    public BookItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();
    }

    @Override
    public void onSetValues(final Book book, PositionInfo positionInfo) {
        Picasso.with(this.getContext()).load(book.imgUrl).into(image);
        name.setText(book.name);
        author.setText(book.author);
        type.setText(book.type);

        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(new BookSelected(book));
            }
        });
    }
}

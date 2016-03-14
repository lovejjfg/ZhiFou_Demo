package com.lovejjfg.zhifou.data;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.lovejjfg.zhifou.data.model.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张俊 on 2016/3/13.
 */
public class ContactUtils {
    private static final String TAG = "联系人";

    /*读取所有的联系人*/
    public static List<ContactBean> getContacts(Context context) throws Exception {
        ArrayList<ContactBean> beans = new ArrayList<>();
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        //获得一个ContentResolver数据共享的对象
        ContentResolver reslover = context.getContentResolver();
        //取得联系人中开始的游标，通过content://com.android.contacts/contacts这个路径获得
        Cursor cursor = reslover.query(uri, null, null, null, null);

        //上边的所有代码可以由这句话代替：Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //Uri.parse("content://com.android.contacts/contacts") == ContactsContract.Contacts.CONTENT_URI
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ContactBean bean = new ContactBean();
                //获得联系人ID
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //获得联系人姓名
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (null == name) {
                    continue;
                }
                bean.setName(name);
                //获得联系人手机号码
                Cursor phone = reslover.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
                if (phone != null) {
                    int columnCount = phone.getCount();
                    if (columnCount < 1) {
                        continue;
                    }
                    for (int i = 0; i < columnCount; i++) {
                        phone.moveToNext();
                        int phoneFieldColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int data2 = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
//                        int phoneFieldColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.);
                        String phoneNumber = phone.getString(phoneFieldColumnIndex);
                        switch (i) {
                            case 0://手机号码
                                bean.setMobile(phoneNumber);
                                break;
                            case 1://公司电话
                                bean.setWorkNumber(phoneNumber);
                                break;
                            case 2://工作手机
                                bean.setWorkMobile(phoneNumber);
                                break;
                            case 3://家庭电话
                                bean.setHomeNumber(phoneNumber);
                                break;
                            default:
                                Log.e("未知类型：", data2 + ":" + phoneNumber);
                                break;
                        }
                    }
                    phone.close();
                }

                String columns[] = {
                        ContactsContract.CommonDataKinds.Event.START_DATE,
                        ContactsContract.CommonDataKinds.Event.TYPE,
                        ContactsContract.CommonDataKinds.Event.MIMETYPE,
                };

                String where = ContactsContract.CommonDataKinds.Event.TYPE + "=" + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY +
                        " and " + ContactsContract.CommonDataKinds.Event.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' and " + ContactsContract.Data.CONTACT_ID + " = " + id;

                String[] selectionArgs = null;
                String sortOrder = ContactsContract.Contacts.DISPLAY_NAME;
                Cursor birthdayCur = reslover.query(ContactsContract.Data.CONTENT_URI, columns, where, selectionArgs, sortOrder);
                String birth = null;
                if (birthdayCur != null && birthdayCur.getCount() > 0) {
                    while (birthdayCur.moveToNext()) {
                        birth = birthdayCur.getString(birthdayCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                    }
                    if (null != birth) {
                        bean.setBirthday(birth);
                    }
                    birthdayCur.close();
                }
                for (ContactBean bean1 : beans) {
                    if (bean1.getName().equals(bean.getName())) {
                        Log.e("存在重复的", bean1.getName());
                        break;
                    }
                }
                beans.add(bean);
            }
            cursor.close();
        }
        return beans;

    }

    //批量添加
    public static void addContact2(Context context,ContactBean bean) throws Exception {
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        ContentProviderOperation op1 = ContentProviderOperation.newInsert(uri)
                .withValue("account_name", null)
                .build();
        operations.add(op1);

        uri = Uri.parse("content://com.android.contacts/data");
        //添加姓名
        ContentProviderOperation op2 = ContentProviderOperation.newInsert(uri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/name")
                .withValue("data2", bean.getName())
                .build();
        operations.add(op2);
        //添加电话号码
        ContentProviderOperation op3 = ContentProviderOperation.newInsert(uri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                .withValue("data1", bean.getMobile())
                .withValue("data2", ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build();
        operations.add(op3);
        if (bean.getHomeNumber() != null) {
            ContentProviderOperation op4 = ContentProviderOperation.newInsert(uri)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data1", bean.getHomeNumber())
                    .withValue("data2", ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build();
            operations.add(op4);
        }
        if (bean.getHomeNumber() != null) {
            ContentProviderOperation op5 = ContentProviderOperation.newInsert(uri)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data1", bean.getWorkMobile())
                    .withValue("data2", ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE)
                    .build();
            operations.add(op5);
        }
        if (bean.getHomeNumber() != null) {
            ContentProviderOperation op6 = ContentProviderOperation.newInsert(uri)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data1", bean.getWorkNumber())
                    .withValue("data2", ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build();
            operations.add(op6);
        }

        //添加电话号码
        ContentProviderOperation op4 = ContentProviderOperation.newInsert(uri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                .withValue("data1", "110")
                .withValue("data2", "1")
                .build();
        operations.add(op4);

        ContentProviderResult[] contentProviderResults = resolver.applyBatch("com.android.contacts", operations);
        contentProviderResults.toString();
    }
}

package Key;
public class KeyObj
{
    public KeyObj() {
        System.loadLibrary("KeyJava");
    }
    final public short UNIKEY_FIND                                =    1;                         // Find UniKey
    final public short UNIKEY_FIND_NEXT          	              =    2;                         // Find next UniKey
    final public short UNIKEY_LOGON                               =    3;                         // Open UniKey
    final public short UNIKEY_LOGOFF                              =    4;                         // Close UniKey
    final public short UNIKEY_READ_MEMORY                         =    5;                         // Read UniKey
    final public short UNIKEY_WRITE_MEMORY                        =    6;                         // Write UniKey
    final public short UNIKEY_RANDOM                              =    7;                         // Generate Random Number
    final public short UNIKEY_SEED                                =    8;                         // Generate Seed Code
    final public short UNIKEY_WRITE_SOFTID                        =    9;                         // Write Software ID
    final public short UNIKEY_READ_SOFTID                         =    10;                        // Read Software ID
    final public short UNIKEY_SET_MODULE                          =    11;                        // Set Module
    final public short UNIKEY_CHECK_MODULE                        =    12;                        // Check Module
    final public short UNIKEY_WRITE_ARITHMETIC                    =    13;                        // Write Arithmetic
    final public short UNIKEY_CALCULATE1                          =    14;                        // Calculate 1
    final public short UNIKEY_CALCULATE2                          =    15;                        // Calculate 2
    final public short UNIKEY_CALCULATE3                          =    16;                        // Calculate 3
    final public short UNIKEY_MODULE_DECRASE                      =    17;                        // Decrease Module Unit
    final public short UNIKEY_SET_NEW_PASSWORD                    =    18;                        // Set New Password via a New Seed
    final public short UNIKEY_GENERATE_KEY                        =    19;                        // Generate a New Key in specific Key Store
    final public short UNIKEY_ENCRYPT                             =    20;                        // Encrypt the Buffer with a Specific Key
    final public short UNIKEY_DECRYPT                             =    21;                        // Decrypt the Buffer with a Specific Key
    final public short UNIKEY_MD5                                 =    22;                        // Make an MD5 Digest for a Certain Content
    final public short UNIKEY_READ_UPDATETAG                      =    23;                        // Read UpdateTag from a Specific Key
    final public short UNIKEY_WRITE_UPDATETAG                     =    24;
    final public short UNIKEY_GET_MODULE                          =    25;                           // Write UpdateTag to a Specific Key
    final public short UNIKEY_GET_TIME                            =    26;                        // read rtc time (lp1,lp2,p1,p2,p3,p4 = year,month,day,hour,minute,second)
    final public short UNIKEY_SET_TIME                            =    27;                        // write rtc time (lp1,lp2,p1,p2,p3,p4 = year,month,day,hour,minute,second)
    final public short UNIKEY_SET_TIME_NOW                        =    28;                        // write rtc time use host time
    final public short UNIKEY_ERASE_TIME_MODULE                   =    29;                        // ERASE TIME MODULE to NULL,
    final public short UNIKEY_SET_TIME_MODULE_START_TIME          =    30;                        // write rtc time module start time(p1,p2,p3,p4 = year,month,day,hour)
    final public short UNIKEY_SET_TIME_MODULE_START_TIME_NOW      =    31;                        // write rtc time module start time use dongle time
    final public short UNIKEY_SET_TIME_MODULE_START_TIME_NOW_PC   =    32;                        // write rtc time module start time use host time
    final public short UNIKEY_SET_TIME_MODULE_END_TIME            =    33;                        // write rtc time module end time (p1,p2,p3,p4 = year,month,day,hour)
    final public short UNIKEY_SET_TIME_MODULE_DURATION            =    34;                        // write rtc time module duration (lp2,p1 = day,hour)=24*day+hour
    final public short UNIKEY_CHECK_TIME_MODULE                   =    35;                        // check if a time >START_TIME and the time < END_TIME(or START_TIME+DURATION) (p1,p2,p3,p4 = year,month,day,hour)
    final public short UNIKEY_CHECK_TIME_MODULE_NOW               =    36;                        // check if mow >START_TIME and now < END_TIME(or START_TIME+DURATION) use dongle time
    final public short UNIKEY_CHECK_TIME_MODULE_NOW_PC            =    37;                        // check if mow >START_TIME and now < END_TIME(or START_TIME+DURATION) use host time
    final public short UNIKEY_GET_MODULE_START_TIME				  =    38;					      // get module start time
	final public short UNIKEY_GET_MODULE_END_TIME				  =    39;		                  // get module end time
                  // Get Module

    // define error code
    //final public int SUCCESS                                    =    0;                         //Success
    final public int OE_ERROR_UNIEKY_NO_UNIKEY                    =    3;                         //No UniKey dongle
    final public int OE_ERROR_UNIEKY_FOUND_INVALID_PASSWORD       =    4;                         //Found UniKey dongle, but Basic passwords are incorrect
    final public int OE_ERROR_UNIEKY_INVALID_PASSWORD_OR_ID       =    5;                         //Wrong password or UniKey HID
    final public int OE_ERROR_UNIEKY_SET_SOFTID                   =    6;                         //Set UniKey ID wrong
    final public int OE_ERROR_INVALID_ADDR_OR_SIZE                =    7;                         //Read/Write address or length is wrong
    final public int OE_ERROR_UNIEKY_UNKNOWN_COMMAND              =    8;                         //No such command
    final public int OE_ERROR_UNIEKY_NOTBELEVEL3                  =    9;                         //Internal Error
    final public int OE_ERROR_UNIEKY_READ_MEMORY                  =    10;                        //Read Error
    final public int OE_ERROR_UNIEKY_WRITE_MEMORY                 =    11;                        //Write Error
    final public int OE_ERROR_UNIEKY_RANDOM                       =    12;                        //Random number Error
    final public int OE_ERROR_UNIEKY_SEED                         =    13;                        //Seed code Error
    final public int OE_ERROR_UNIEKY_CALCULATE                    =    14;                        //Calculate Error
    final public int OE_ERROR_UNIEKY_NO_OPEN                      =    15;                        //No open dongle before operating dongle
    final public int OE_ERROR_UNIEKY_OPEN_OVERFLOW                =    16;                        //Too many open dongles (>16)
    final public int OE_ERROR_UNIEKY_NOMORE                       =    17;                        //No more dongle
    final public int OE_ERROR_UNIEKY_NEED_FIND                    =    18;                        //No Find before FindNext
    final public int OE_ERROR_UNIEKY_MODULE                       =    19;                        //License module error
    final public int OE_ERROR_UNIEKY_AR_BAD_COMMAND               =    20;                        //Arithmetic instruction Error
    final public int OE_ERROR_UNIEKY_AR_UNKNOWN_OPCODE            =    21;                        //Arithmetic operator Error
    final public int OE_ERROR_UNIEKY_AR_WRON_GBEGIN               =    22;                        //Const number can't use on first arithmetic instruction
    final public int OE_ERROR_UNIEKY_AR_WRONG_END                 =    23;                        //Const number can't use on last arithmetic instruction
    final public int OE_ERROR_UNIEKY_AR_VALUE_OVERFLOW            =    24;                        //Const number > 63
    final public int OE_ERROR_UNIEKY_TOO_MUCH_THREAD              =    25;                        //Too many (>100) threads in the single process open the dongle
    final public int OE_ERROR_UNIEKY_INVALID_KEY                  =    26;                        //The key in the key storage is wrong
    final public int OE_ERROR_UNIEKY_VERIFY_ADV_PASSWORD          =    27;                        //Advanced passwords (password3 and passowrd4) not verified
    final public int OE_ERROR_UNIEKY_INVALID_KEY_STORE            =    28;                        //Wrong key storage number
    final public int OE_EEROR_UNIEKY_GENERATE_NEW_PASSWORD        =    29;                        //Generate new password Error
    final public int OE_ERROR_UNIEKY_READ_UPDATETAG               =    30;                        //Read UpdateTag error
    final public int OE_ERROR_UNIEKY_WRITE_UPDATETAG              =    31;                        //Write UpdateTag error
    final public int OE_ERROR_UNIEKY_ENCRYPT_FAILED               =    32;                        //Encrypt data error
    final public int OE_ERROR_UNIEKY_DECRYPT_FAILED               =    33;                        //Decrypt data error
    final public int OE_ERROR_UNIEKY_READ_TIME                    =    34;                        //Get UniKey time error
    final public int OE_ERROR_UNIEKY_WRITE_TIME                   =    35;                        //Set UniKey time error
    final public int OE_ERROR_UNIEKY_WRITE_TIME_MODULE            =    36;                        //Write time module error
    final public int OE_ERROR_UNIEKY_COMOPARE_TIME_MODULE         =    37;                        //The specific time is before the module’s start time
    final public int OE_ERROR_UNIEKY_TIME_MODULE_NOT_NULL         =    38;                        //The real time module is not null, this error is returned when write once flag is set
    final public int OE_ERROR_UNIEKY_TIME_MODULE_OVERDUR          =    39;                        //The specific time is later than the module’s end time, or the module is expired
    final public int OE_ERROR_UNIEKY_UNIKEY_ALREADY_LOCKED        =    41;                        //The specific time is later than the module’s end time, or the module is expired

    final public int ERROR_RECEIVE_NULL                           =    0x100;                     //Receive null
    final public int ERROR_UNKNOWN_SYSTEM                         =    0x102;                     //Unknown operating system
    final public int ERROR_UNKNOWN_ERROR                          =    0xffff;                    //Unknown Error

    final public int NET_UNIEKY_ERROR_BASE                     =    100;                          // net unikey error base
    final public int NET_UNIKEY_MEMORY_ERROR                   =    NET_UNIEKY_ERROR_BASE+1;      // memory allocation error
    final public int NET_UNIKEY_SEND_ERROR                     =    NET_UNIEKY_ERROR_BASE+2;      // send error
    final public int NET_UNIEY_RECEIVE_ERROR                   =    NET_UNIEKY_ERROR_BASE+3;      // receive error
    final public int NET_UNIKEY_MESSAGE_WRONG                  =    NET_UNIEKY_ERROR_BASE+4;      // communication message is modified
    final public int NET_UNIKEY_SETUP_SOCKET_ERROR             =    NET_UNIEKY_ERROR_BASE+5;      // setup socket error
    final public int NET_UNIKEY_CLIENT_EXSIT                   =    NET_UNIEKY_ERROR_BASE+6;      // the client already exists, per this mode
    final public int NET_UNIKEY_TOO_MANY_CLIENT                =    NET_UNIEKY_ERROR_BASE+7;      // the number of client reach the limitation
    final public int NET_UNIKEY_IN_BLACKLIST                   =    NET_UNIEKY_ERROR_BASE+8;      // the client is in the black list
    final public int NET_UNIKEY_OUT_WHITELIST                  =    NET_UNIEKY_ERROR_BASE+9;      // the client is not in the white list
    final public int NET_UNIKEY_MESSAGE_CHANGE                 =    NET_UNIEKY_ERROR_BASE+10;     // the message packet was changed
    final public int NET_UNIKEY_AREADY_START                   =    NET_UNIEKY_ERROR_BASE+11;     // the server has already started
    final public int NET_UNIKEY_SOCKET_INIT_FAILED             =    NET_UNIEKY_ERROR_BASE+12;     // cannot initialize the socket
    final public int NET_UNIKEY_SOCKET_BIND_FAILED             =    NET_UNIEKY_ERROR_BASE+13;     // cannot bind the port with the socket
    final public int NET_UNIKEY_SOCKET_LISTEN_FAILED           =    NET_UNIEKY_ERROR_BASE+14;     // cannot start listening with the socket
    final public int NET_UNIKEY_START_UDP_SERVER_FAILED        =    NET_UNIEKY_ERROR_BASE+15;     // start udp server failed
    final public int NET_UNIKEY_TOO_LONG_MESSAGE               =    NET_UNIEKY_ERROR_BASE+16;     // too long message
    final public int NET_UNIKEY_NOT_WORKING                    =    NET_UNIEKY_ERROR_BASE+17;     // cannot connect remote netunikey server
    final public int NET_UNIKEY_DISCARD_BY_SERVER              =    NET_UNIEKY_ERROR_BASE+18;     // the client is discarded by the server

    // define error code
    final public int SUCCESS                                   =    0;                            //Success
    final public int ERROR_UNIEKY_NO_UNIKEY                    =    200;                          //No UniKey dongle
    final public int ERROR_UNIEKY_FOUND_INVALID_PASSWORD       =    201;                          //Found UniKey dongle, but Basic passwords are incorrect
    final public int ERROR_UNIEKY_INVALID_PASSWORD_OR_ID       =    202;                          //Wrong password or UniKey HID
    final public int ERROR_UNIEKY_SET_SOFTID                   =    203;                          //Set UniKey ID wrong
    final public int ERROR_INVALID_ADDR_OR_SIZE                =    204;                          //Read/Write address or length is wrong
    final public int ERROR_UNIEKY_UNKNOWN_COMMAND              =    205;                          //No such command
    final public int ERROR_UNIEKY_NOTBELEVEL3                  =    206;                          //Internal Error
    final public int ERROR_UNIEKY_READ_MEMORY                  =    207;                          //Read Error
    final public int ERROR_UNIEKY_WRITE_MEMORY                 =    208;                          //Write Error
    final public int ERROR_UNIEKY_RANDOM                       =    209;                          //Random number Error
    final public int ERROR_UNIEKY_SEED                         =    210;                          //Seed code Error
    final public int ERROR_UNIEKY_CALCULATE                    =    211;                          //Calculate Error
    final public int ERROR_UNIEKY_NO_OPEN                      =    212;                          //No open dongle before operating dongle
    final public int ERROR_UNIEKY_OPEN_OVERFLOW                =    213;                          //Too many open dongles (>16)
    final public int ERROR_UNIEKY_NOMORE                       =    214;                          //No more dongle
    final public int ERROR_UNIEKY_NEED_FIND                    =    215;                          //No Find before FindNext
    final public int ERROR_UNIEKY_MODULE                       =    216;                          //License module error
    final public int ERROR_UNIEKY_AR_BAD_COMMAND               =    217;                          //Arithmetic instruction Error
    final public int ERROR_UNIEKY_AR_UNKNOWN_OPCODE            =    218;                          //Arithmetic operator Error
    final public int ERROR_UNIEKY_AR_WRON_GBEGIN               =    219;                          //Const number can't use on first arithmetic instruction
    final public int ERROR_UNIEKY_AR_WRONG_END                 =    220;                          //Const number can't use on last arithmetic instruction
    final public int ERROR_UNIEKY_AR_VALUE_OVERFLOW            =    221;                          //Const number > 63
    final public int ERROR_UNIEKY_TOO_MUCH_THREAD              =    280;                          //Too many (>100) threads in the single process open the dongle
    final public int ERROR_UNIEKY_INVALID_KEY                  =    222;                          //The key in the key storage is wrong
    final public int ERROR_UNIEKY_VERIFY_ADV_PASSWORD          =    223;                          //Advanced passwords (password3 and passowrd4) not verified
    final public int ERROR_UNIEKY_INVALID_KEY_STORE            =    224;                          //Wrong key storage number
    final public int EEROR_UNIEKY_GENERATE_NEW_PASSWORD        =    225;                          //Generate new password Error
    final public int ERROR_UNIEKY_READ_UPDATETAG               =    226;                          //Read UpdateTag error
    final public int ERROR_UNIEKY_WRITE_UPDATETAG              =    227;                          //Write UpdateTag error
    final public int ERROR_UNIEKY_ENCRYPT_FAILED               =    228;                          //Encrypt data error
    final public int ERROR_UNIEKY_DECRYPT_FAILED               =    229;                          //Decrypt data error
    final public int ERROR_UNIEKY_READ_TIME                    =    230;                          //Get UniKey time error
    final public int ERROR_UNIEKY_WRITE_TIME                   =    231;                          //Set UniKey time error
    final public int ERROR_UNIEKY_WRITE_TIME_MODULE            =    232;                          //Write time module error
    final public int ERROR_UNIEKY_COMOPARE_TIME_MODULE         =    233;                          //The specific time is before the module’s start time
    final public int ERROR_UNIEKY_TIME_MODULE_NOT_NULL         =    234;                          //The real time module is not null, this error is returned when write once flag is set
    final public int ERROR_UNIEKY_TIME_MODULE_OVERDUR          =    235;                          //The specific time is later than the module’s end time, or the module is expired
    final public int ERROR_UNIEKY_UNIKEY_ALREADY_LOCKED        =    236;                          //The specific time is later than the module’s end time, or the module is expired

    public native int UniKey(short func, short[] handle, int[] lp1, int[] lp2, short[] p1, short[] p2, short[] p3, short[] p4, byte[] buffer);

    public int __UniKey(short func, short[] handle, int[] lp1, int[] lp2, short[] p1, short[] p2, short[] p3, short[] p4, byte[] buffer)
    {
        int ret = 0;
        ret = UniKey(func, handle, lp1, lp2, p1, p2, p3, p4, buffer);
        if(ret==OE_ERROR_UNIEKY_NO_UNIKEY)return ERROR_UNIEKY_NO_UNIKEY;
        if(ret==OE_ERROR_UNIEKY_INVALID_PASSWORD_OR_ID)return ERROR_UNIEKY_INVALID_PASSWORD_OR_ID;
        if(ret==OE_ERROR_UNIEKY_SET_SOFTID)return ERROR_UNIEKY_SET_SOFTID;
        if(ret==OE_ERROR_INVALID_ADDR_OR_SIZE)return ERROR_INVALID_ADDR_OR_SIZE;
        if(ret==OE_ERROR_UNIEKY_UNKNOWN_COMMAND)return ERROR_UNIEKY_UNKNOWN_COMMAND;
        if(ret==OE_ERROR_UNIEKY_NOTBELEVEL3)return ERROR_UNIEKY_NOTBELEVEL3;
        if(ret==OE_ERROR_UNIEKY_READ_MEMORY)return ERROR_UNIEKY_READ_MEMORY;
        if(ret==OE_ERROR_UNIEKY_WRITE_MEMORY)return ERROR_UNIEKY_WRITE_MEMORY;
        if(ret==OE_ERROR_UNIEKY_RANDOM)return ERROR_UNIEKY_RANDOM;
        if(ret==OE_ERROR_UNIEKY_SEED)return ERROR_UNIEKY_SEED;
        if(ret==OE_ERROR_UNIEKY_CALCULATE)return ERROR_UNIEKY_CALCULATE;
        if(ret==OE_ERROR_UNIEKY_NO_OPEN)return ERROR_UNIEKY_NO_OPEN;
        if(ret==OE_ERROR_UNIEKY_OPEN_OVERFLOW)return ERROR_UNIEKY_OPEN_OVERFLOW;
        if(ret==OE_ERROR_UNIEKY_NOMORE)return ERROR_UNIEKY_NOMORE;
        if(ret==OE_ERROR_UNIEKY_NEED_FIND)return ERROR_UNIEKY_NEED_FIND;
        if(ret==OE_ERROR_UNIEKY_MODULE)return ERROR_UNIEKY_MODULE;
        if(ret==OE_ERROR_UNIEKY_AR_BAD_COMMAND)return ERROR_UNIEKY_AR_BAD_COMMAND;
        if(ret==OE_ERROR_UNIEKY_AR_UNKNOWN_OPCODE)return ERROR_UNIEKY_AR_UNKNOWN_OPCODE;
        if(ret==OE_ERROR_UNIEKY_AR_WRON_GBEGIN)return ERROR_UNIEKY_AR_WRON_GBEGIN;
        if(ret==OE_ERROR_UNIEKY_AR_WRONG_END)return ERROR_UNIEKY_AR_WRONG_END;
        if(ret==OE_ERROR_UNIEKY_AR_VALUE_OVERFLOW)return ERROR_UNIEKY_AR_VALUE_OVERFLOW;
        if(ret==OE_ERROR_UNIEKY_TOO_MUCH_THREAD)return ERROR_UNIEKY_TOO_MUCH_THREAD;
        if(ret==OE_ERROR_UNIEKY_INVALID_KEY)return ERROR_UNIEKY_INVALID_KEY;
        if(ret==OE_ERROR_UNIEKY_VERIFY_ADV_PASSWORD)return ERROR_UNIEKY_VERIFY_ADV_PASSWORD;
        if(ret==OE_ERROR_UNIEKY_INVALID_KEY_STORE)return ERROR_UNIEKY_INVALID_KEY_STORE;
        if(ret==OE_EEROR_UNIEKY_GENERATE_NEW_PASSWORD)return EEROR_UNIEKY_GENERATE_NEW_PASSWORD;
        if(ret==OE_ERROR_UNIEKY_READ_UPDATETAG)return ERROR_UNIEKY_READ_UPDATETAG;
        if(ret==OE_ERROR_UNIEKY_WRITE_UPDATETAG)return ERROR_UNIEKY_WRITE_UPDATETAG;
        if(ret==OE_ERROR_UNIEKY_ENCRYPT_FAILED)return ERROR_UNIEKY_ENCRYPT_FAILED;
        if(ret==OE_ERROR_UNIEKY_DECRYPT_FAILED)return ERROR_UNIEKY_DECRYPT_FAILED;
        if(ret==OE_ERROR_UNIEKY_READ_TIME)return ERROR_UNIEKY_READ_TIME;
        if(ret==OE_ERROR_UNIEKY_WRITE_TIME)return ERROR_UNIEKY_WRITE_TIME;
        if(ret==OE_ERROR_UNIEKY_WRITE_TIME_MODULE)return ERROR_UNIEKY_WRITE_TIME_MODULE;
        if(ret==OE_ERROR_UNIEKY_COMOPARE_TIME_MODULE)return ERROR_UNIEKY_COMOPARE_TIME_MODULE;
        if(ret==OE_ERROR_UNIEKY_TIME_MODULE_NOT_NULL)return ERROR_UNIEKY_TIME_MODULE_NOT_NULL;
        if(ret==OE_ERROR_UNIEKY_TIME_MODULE_OVERDUR)return ERROR_UNIEKY_TIME_MODULE_OVERDUR;
        if(ret==OE_ERROR_UNIEKY_UNIKEY_ALREADY_LOCKED)return ERROR_UNIEKY_UNIKEY_ALREADY_LOCKED;
        return ret;
    }
    // New unikey API depend on old.
    public int UniKey_Find(short[] pHandle, int[] pSetting1, int[] pSetting2)
    {
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_FIND, pHandle, pSetting1, pSetting2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Find_Next(short[] pHandle, int[] pSetting1, int[] pSetting2){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_FIND_NEXT, pHandle, pSetting1, pSetting2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_User_Logon(short[] pHandle, short[] pPassword1, short[] pPassword2){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_LOGON, pHandle, _lp1, _lp2, pPassword1, pPassword2, _p3, _p4, _buffer);
    };

    public int UniKey_Vender_Logon(short[] pHandle, short[] pPassword1, short[] pPassword2, short[] pPassword3, short[] pPassword4){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_LOGON, pHandle, _lp1, _lp2, pPassword1, pPassword2, pPassword3, pPassword4, _buffer);
    };

    public int UniKey_Logoff(short[] pHandle){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_LOGOFF, pHandle, _lp1, _lp2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Read_Memory(short[] pHandle, short[] pStartAddress, short[] pBufferLength, byte[] pBuffer){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_READ_MEMORY, pHandle, _lp1, _lp2, pStartAddress, pBufferLength, _p3, _p4, pBuffer);
    };

    public int UniKey_Write_Memory(short[] pHandle, short[] pStartAddress, short[] pBufferLength, byte[] pBuffer){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_WRITE_MEMORY, pHandle, _lp1, _lp2, pStartAddress, pBufferLength, _p3, _p4, pBuffer);
    };

    public int UniKey_Random(short[] pHandle, short[] pReturn1, short[] pReturn2, short[] pReturn3, short[] pReturn4){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_RANDOM, pHandle, _lp1, _lp2, pReturn1, pReturn2, pReturn3,pReturn4, _buffer);
    };

    public int UniKey_Seed(short[] pHandle, int[] pSeed, short[] pReturn1, short[] pReturn2, short[] pReturn3, short[] pReturn4){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_SEED, pHandle, pSeed, _lp2, pReturn1, pReturn2, pReturn3, pReturn4, _buffer);
    };

    public int UniKey_Write_SoftID(short[] pHandle, int[] pSoftID){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_WRITE_SOFTID, pHandle, pSoftID, _lp2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Read_SoftID(short[] pHandle, int[] pSoftID){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_READ_SOFTID, pHandle, pSoftID, _lp2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Write_UpDataTag(short[] pHandle, int[] pUpDataTag){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_WRITE_UPDATETAG, pHandle, pUpDataTag, _lp2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Read_UpDataTag(short[] pHandle, int[] pUpDataTag){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_READ_UPDATETAG, pHandle, pUpDataTag, _lp2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Set_Module(short[] pHandle, short[] pModule, short[] pValue, short[] pDecrease){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_SET_MODULE, pHandle, _lp1, _lp2, pModule, pValue, pDecrease, _p4, _buffer);
    };

     public int UniKey_Get_Module(short[] pHandle, short[] pModule, short[] pValue){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_GET_MODULE, pHandle, _lp1, _lp2, pModule, _p2 ,pValue, _p4, _buffer);
    };

    public int UniKey_Check_Module(short[] pHandle, short[] pModule, short[] pValue, short[] pDecrease){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_CHECK_MODULE, pHandle, _lp1, _lp2, pModule, pValue, pDecrease, _p4, _buffer);
    };

    public int UniKey_Module_Decrase(short[] pHandle, short[] pModule){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_MODULE_DECRASE, pHandle, _lp1, _lp2, pModule, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Generate_New_Password(short[] pHandle, int[] pSeed, short[] pPassword1, short[] pPassword2, short[] pPassword3, short[] pPassword4){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_SET_NEW_PASSWORD, pHandle, pSeed, _lp2, pPassword1, pPassword2, pPassword3, pPassword4, _buffer);
    };

    public int UniKey_Generate_Key(short[] pHandle, short[] pKeyNumber){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_GENERATE_KEY, pHandle, _lp1, _lp2, pKeyNumber, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Encrypt(short[] pHandle, int[] pBufferLength, int[] pKeyNumber, byte[] pBuffer){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_ENCRYPT, pHandle, pBufferLength, pKeyNumber, _p1, _p2, _p3, _p4, pBuffer);
    };

    public int UniKey_Decrypt(short[] pHandle, int[] lp1, int[] lp2, byte[] buffer){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_DECRYPT, pHandle, lp1, lp2, _p1, _p2, _p3, _p4, buffer);
    };

    public int UniKey_MD5(short[] pHandle, int[] pBufferLength, byte[] pBuffer){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_MD5, pHandle, pBufferLength, _lp2, _p1, _p2, _p3, _p4, pBuffer);
    };


    public int UniKey_Set_Time(short[] pHandle, int[] pYear, int[] pMonth, short[] pDay, short[] pHour, short[] pMinute, short[] pSecond){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_SET_TIME, pHandle, pYear, pMonth, pDay, pHour, pMinute, pSecond, _buffer);
    };

    public int UniKey_Set_Time_Now(short[] pHandle){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_SET_TIME_NOW, pHandle, _lp1, _lp2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Get_Time(short[] pHandle, int[] pYear, int[] pMonth, short[] pDay, short[] pHour, short[] pMinute, short[] pSecond){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_GET_TIME, pHandle, pYear, pMonth, pDay, pHour, pMinute, pSecond, _buffer);
    };

    public int UniKey_Erase_Time_Module(short[] pHandle, int[] pModule){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_ERASE_TIME_MODULE, pHandle, pModule, _lp2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Set_Time_Module_Start_Time(short[] pHandle, int[] pModule, short[] pYear, short[] pMonth, short[] pDay, short[] pHour){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_SET_TIME_MODULE_START_TIME, pHandle, pModule, _lp2, pYear, pMonth, pDay, pHour, _buffer);
    };

    public int UniKey_Set_Time_Module_Start_Time_Now(short[] pHandle, int[] pModule){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_SET_TIME_MODULE_START_TIME_NOW, pHandle, pModule, _lp2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Set_Time_Module_Start_Time_Now_PC(short[] pHandle, int[] pModule){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_SET_TIME_MODULE_START_TIME_NOW_PC, pHandle, pModule, _lp2, _p1, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Set_Time_Module_End_Time(short[] pHandle, int[] pModule, short[] pYear, short[] pMonth, short[] pDay, short[] pHour){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_SET_TIME_MODULE_END_TIME, pHandle, pModule, _lp2, pYear, pMonth, pDay, pHour, _buffer);
    };

    public int UniKey_Set_Time_Module_Duration(short[] pHandle, int[] pModule, int[] pDays, short[] pHours){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_SET_TIME_MODULE_DURATION, pHandle, pModule, pDays, pHours, _p2, _p3, _p4, _buffer);
    };

    public int UniKey_Check_Time_Module(short[] pHandle, int[] pModule, short[] pYear, short[] pMonth, short[] pDay, short[] pHour){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_CHECK_TIME_MODULE, pHandle, pModule, _lp2, pYear, pMonth, pDay, pHour, _buffer);
    };

    public int UniKey_Check_Time_Module_Now(short[] pHandle, int[] pModule, int[] lp2, short[] p1){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_CHECK_TIME_MODULE_NOW, pHandle, pModule, lp2, p1, _p2, _p3, _p4, _buffer);
    };
    
    public int UniKey_Check_Time_Module_Now_PC(short[] pHandle, int[] pModule, int[] lp2, short[] p1){
        int[] _lp1 = {0};
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_CHECK_TIME_MODULE_NOW_PC, pHandle, pModule, lp2, p1, _p2, _p3, _p4, _buffer);
    };

     public int UniKey_Get_Module_Start_Time(short[] pHandle, int[] lp1 ,short[] p1, short[] p2, short[] p3, short[] p4){
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_GET_MODULE_START_TIME, pHandle, lp1, _lp2, p1, p2, p3, p4, _buffer);
    };
     public int UniKey_Get_Module_End_Time(short[] pHandle, int[] lp1 ,short[] p1, short[] p2, short[] p3, short[] p4){
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey(UNIKEY_GET_MODULE_END_TIME, pHandle, lp1, _lp2, p1, p2, p3, p4, _buffer);
    };
    public int UniKey_Get_Version(short[] pHandle, int[] lp1){
        lp1[0] = 0x00000200;
        return 0;
    };

    public int UniKey_Get_Dongle_Location_(short[] pHandle, int[] lp1){
        int[] _lp2 = {0};
        short[] _p1 = {0};
        short[] _p2 = {0};
        short[] _p3 = {0};
        short[] _p4 = {0};
        byte[] _buffer = new byte[16];
        return __UniKey((short)0xFFFF, pHandle, lp1, _lp2, _p1, _p2, _p3, _p4, _buffer);
    };




}



